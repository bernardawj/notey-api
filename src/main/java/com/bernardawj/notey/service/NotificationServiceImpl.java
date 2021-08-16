package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.DeleteNotificationDTO;
import com.bernardawj.notey.dto.notification.GetNotificationDTO;
import com.bernardawj.notey.dto.notification.NotificationDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.Notification;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.repository.NotificationRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final String NOTIFICATION_NOT_FOUND = "NotificationService.NOTIFICATION_NOT_FOUND";

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createNotification(CreateNotificationDTO createNotificationDTO) throws NotificationServiceException {
        // Check if user exists
        List<User> users = this.userRepository.findUsersByFromUserIdAndToUserId(createNotificationDTO.getFromUserId(),
                createNotificationDTO.getToUserId());
        if (users.size() != 2)
            throw new NotificationServiceException("NotificationService.INVALID_USERS");

        // Create notification
        User fromUser = null, toUser = null;
        for (User user : users) {
            if (user.getId().intValue() == createNotificationDTO.getFromUserId().intValue())
                fromUser = user;
            else
                toUser = user;
        }
        Notification notification = new Notification(createNotificationDTO.getMessage(),
                createNotificationDTO.getType(), LocalDateTime.now(ZoneOffset.UTC), fromUser, toUser);
        this.notificationRepository.save(notification);
    }

    @Override
    public NotificationDTO getNotification(GetNotificationDTO getNotificationDTO) throws NotificationServiceException {
        // Check if notifications exists
        Optional<Notification> optNotification =
                this.notificationRepository.findByNotificationIdAndUserId(getNotificationDTO.getNotificationId(),
                        getNotificationDTO.getUserId());
        Notification notification =
                optNotification.orElseThrow(() -> new NotificationServiceException(NOTIFICATION_NOT_FOUND));

        // Return DTO
        return populateToDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllUserNotifications(Integer userId) {
        // Check if notifications of user exists
        Iterable<Notification> notifications = this.notificationRepository.findAllByUserId(userId);

        // Return DTO
        return populateToListDTO(notifications);
    }

    @Override
    public void clearAllUserNotifications(Integer userId) {
        // Check if notifications exists
        Iterable<Notification> notifications = this.notificationRepository.findAllByUserId(userId);

        // Delete from database
        this.notificationRepository.deleteAll(notifications);
    }

    @Override
    public void deleteNotification(DeleteNotificationDTO deleteNotificationDTO) throws NotificationServiceException {
        // Check if notification exists
        Optional<Notification> optNotification =
                this.notificationRepository.findByNotificationIdAndUserId(deleteNotificationDTO.getNotificationId(),
                        deleteNotificationDTO.getUserId());
        Notification notification =
                optNotification.orElseThrow(() -> new NotificationServiceException(NOTIFICATION_NOT_FOUND));

        // Delete from database
        this.notificationRepository.deleteById(notification.getId());
    }

    private NotificationDTO populateToDTO(Notification notification) {
        UserDTO fromUser = new UserDTO(notification.getFromUser().getId(), notification.getFromUser().getEmail(),
                null, notification.getFromUser().getFirstName(), notification.getFromUser().getLastName());

        UserDTO toUser = new UserDTO(notification.getUser().getId(), notification.getUser().getEmail(),
                null, notification.getUser().getFirstName(), notification.getUser().getLastName());

        return new NotificationDTO(notification.getId(), notification.getMessage(),
                notification.getType(), notification.getCreatedAt(), fromUser, toUser);
    }

    private List<NotificationDTO> populateToListDTO(Iterable<Notification> notifications) {
        List<NotificationDTO> notificationsDTO = new ArrayList<>();
        notifications.forEach(notification -> {
            notificationsDTO.add(populateToDTO(notification));
        });
        return notificationsDTO;
    }
}

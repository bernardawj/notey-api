package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.DeleteNotificationDTO;
import com.bernardawj.notey.dto.notification.GetNotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.repository.NotificationRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void invalidFromUserIdAndToUserIdOnCreateNotificationShouldThrowException() {
        // Mock DTO
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();
        createNotificationDTO.setFromUserId(1);
        createNotificationDTO.setToUserId(2);

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findUsersByFromUserIdAndToUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        // Check if method indeed throws an exception
        NotificationServiceException ex = Assertions.assertThrows(NotificationServiceException.class,
                () -> this.notificationService.createNotification(createNotificationDTO));

        // Check if exception message is the same
        Assertions.assertEquals("NotificationService.INVALID_USERS", ex.getMessage());
    }

    @Test
    public void invalidNotificationIdAndUserIdOnGetNotificationShouldThrowException() {
        // Mock DTO
        GetNotificationDTO getNotificationDTO = new GetNotificationDTO();
        getNotificationDTO.setNotificationId(1);
        getNotificationDTO.setUserId(1);

        // Mock the behavior of repository
        Mockito.when(this.notificationRepository.findByNotificationIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        NotificationServiceException ex = Assertions.assertThrows(NotificationServiceException.class,
                () -> this.notificationService.getNotification(getNotificationDTO));

        // Check if exception message is the same
        Assertions.assertEquals("NotificationService.NOTIFICATION_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidNotificationIdAndUserIdOnDeleteNotificationShouldThrowException() {
        // Mock DTO
        DeleteNotificationDTO deleteNotificationDTO = new DeleteNotificationDTO();
        deleteNotificationDTO.setNotificationId(1);
        deleteNotificationDTO.setUserId(1);

        // Mock the behavior of repository
        Mockito.when(this.notificationRepository.findByNotificationIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        NotificationServiceException ex = Assertions.assertThrows(NotificationServiceException.class,
                () -> this.notificationService.deleteNotification(deleteNotificationDTO));

        // Check if exception message is the same
        Assertions.assertEquals("NotificationService.NOTIFICATION_NOT_FOUND", ex.getMessage());
    }
}

package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.DeleteNotificationDTO;
import com.bernardawj.notey.dto.notification.GetNotificationDTO;
import com.bernardawj.notey.dto.notification.NotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;

import java.util.List;

public interface NotificationService {

    void createNotification(CreateNotificationDTO createNotificationDTO) throws NotificationServiceException;

    NotificationDTO getNotification(GetNotificationDTO getNotificationDTO) throws NotificationServiceException;

    List<NotificationDTO> getAllUserNotifications(Integer userId);

    void deleteNotification(DeleteNotificationDTO deleteNotificationDTO) throws NotificationServiceException;
}

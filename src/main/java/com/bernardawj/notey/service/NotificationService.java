package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.NotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;

import java.util.List;

public interface NotificationService {

    void createNotification(CreateNotificationDTO createNotificationDTO) throws NotificationServiceException;

    List<NotificationDTO> getAllUserNotifications(Integer userId);

    void clearAllUserNotifications(Integer userId);
}

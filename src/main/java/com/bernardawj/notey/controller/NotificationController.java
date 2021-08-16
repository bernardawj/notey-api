package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.DeleteNotificationDTO;
import com.bernardawj.notey.dto.notification.GetNotificationDTO;
import com.bernardawj.notey.dto.notification.NotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping()
    public ResponseEntity<Void> createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) throws NotificationServiceException {
        this.notificationService.createNotification(createNotificationDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<NotificationDTO> getNotification(@RequestBody GetNotificationDTO getNotificationDTO) throws NotificationServiceException {
        NotificationDTO notificationDTO = this.notificationService.getNotification(getNotificationDTO);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllUserNotifications(@PathVariable("userId") Integer userId) throws NotificationServiceException {
        List<NotificationDTO> notificationsDTO = this.notificationService.getAllUserNotifications(userId);
        return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> clearAllUserNotifications(@PathVariable("userId") Integer userId) throws NotificationServiceException {
        this.notificationService.clearAllUserNotifications(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteNotification(@RequestBody DeleteNotificationDTO deleteNotificationDTO) throws NotificationServiceException {
        this.notificationService.deleteNotification(deleteNotificationDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

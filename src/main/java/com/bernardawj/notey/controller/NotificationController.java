package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.notification.NotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/notification")
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping()
    public ResponseEntity<Void> createNotification(@RequestBody @Valid CreateNotificationDTO createNotificationDTO) throws NotificationServiceException {
        this.notificationService.createNotification(createNotificationDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllUserNotifications(@PathVariable("userId") Integer userId) throws NotificationServiceException {
        List<NotificationDTO> notificationsDTO = this.notificationService.getAllUserNotifications(userId);
        return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Void> clearAllUserNotifications(@PathVariable("userId") Integer userId) throws NotificationServiceException {
        this.notificationService.clearAllUserNotifications(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

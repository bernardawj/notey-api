package com.bernardawj.notey.dto.notification;

import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Integer id;
    private String message;
    private NotificationType type;
    private LocalDateTime createdAt;

    private UserDTO fromUser;
    private UserDTO toUser;

    public NotificationDTO() {
    }

    public NotificationDTO(Integer id, String message, NotificationType type, LocalDateTime createdAt,
                           UserDTO fromUser, UserDTO toUser) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public UserDTO getToUser() {
        return toUser;
    }

    public void setToUser(UserDTO toUser) {
        this.toUser = toUser;
    }
}

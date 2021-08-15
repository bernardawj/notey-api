package com.bernardawj.notey.dto.notification;

import com.bernardawj.notey.enums.NotificationType;

public class CreateNotificationDTO {

    private String message;
    private NotificationType type;
    private Integer fromUserId;
    private Integer toUserId;

    public CreateNotificationDTO() {
    }

    public CreateNotificationDTO(String message, NotificationType type, Integer fromUserId, Integer toUserId) {
        this.message = message;
        this.type = type;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
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

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
}

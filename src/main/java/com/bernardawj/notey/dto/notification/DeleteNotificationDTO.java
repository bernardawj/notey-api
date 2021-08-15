package com.bernardawj.notey.dto.notification;

public class DeleteNotificationDTO {

    private Integer notificationId;
    private Integer userId;

    public DeleteNotificationDTO() {
    }

    public DeleteNotificationDTO(Integer notificationId, Integer userId) {
        this.notificationId = notificationId;
        this.userId = userId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

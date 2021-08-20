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

    public static CreateNotificationDTO notificationBuilder(String itemName, String fromUserName,
                                                            NotificationType notificationType, Integer fromUserId,
                                                            Integer toUserId) {
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();

        String message = null;
        switch (notificationType) {
            case PROJECT_INVITATION:
                // Project name and from manager's name
                message = String.format("Project (%s) has been assigned to you by %s.", itemName, fromUserName);
                break;
            case PROJECT_REMOVAL:
                // Project name and from manager's name
                message = String.format("You have been removed from Project (%s) by %s.", itemName, fromUserName);
                break;
            case PROJECT_ACCEPTANCE:
                // Project name and user's name
                message = String.format("Assignment on Project (%s) has been accepted by %s.", itemName, fromUserName);
                break;
            case PROJECT_REJECTION:
                // Project name and user's name
                message = String.format("Assignment on Project (%s) has been rejected by %s.", itemName, fromUserName);
                break;
            case TASK_ALLOCATION:
                // Task name and manager's name
                message = String.format("Task (%s) has been assigned to you by %s.", itemName, fromUserName);
                break;
            case TASK_ALLOCATION_REMOVAL:
                // Task name and manager's name
                message = String.format("Task (%s) has been removed from you by %s.", itemName, fromUserName);
                break;
            case TASK_MARK_COMPLETED:
                // Task name and user's name
                message = String.format("Task (%s) has been marked completed by %s.", itemName, fromUserName);
                break;
            case TASK_MARK_INCOMPLETE:
                // Task name and user's name
                message = String.format("Task (%s) has been marked incomplete by %s.", itemName, fromUserName);
                break;
        }

        createNotificationDTO.setMessage(message);
        createNotificationDTO.setType(notificationType);
        createNotificationDTO.setFromUserId(fromUserId);
        createNotificationDTO.setToUserId(toUserId);

        return createNotificationDTO;
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

package com.bernardawj.notey.dto.task;

public class MarkTaskCompletionDTO {

    private Integer taskId;
    private Integer userId;
    private Boolean complete;

    public MarkTaskCompletionDTO() {
    }

    public MarkTaskCompletionDTO(Integer taskId, Integer userId, Boolean complete) {
        this.taskId = taskId;
        this.userId = userId;
        this.complete = complete;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}

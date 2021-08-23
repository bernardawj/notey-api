package com.bernardawj.notey.dto.task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MarkTaskCompletionDTO {

    @NotNull(message = "{task.id.empty}")
    @Min(value = 1, message = "{task.id.length}")
    private Integer taskId;

    @NotNull(message = "{task.userId.empty}")
    @Min(value = 1, message = "{task.userId.length}")
    private Integer userId;

    @NotNull(message = "{task.isCompleted.empty}")
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

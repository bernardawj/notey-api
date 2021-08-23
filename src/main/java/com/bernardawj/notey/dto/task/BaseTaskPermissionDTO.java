package com.bernardawj.notey.dto.task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class BaseTaskPermissionDTO {

    @NotNull(message = "{task.id.empty}")
    @Min(value = 1, message = "{task.id.min}")
    private Integer taskId;

    @NotNull(message = "{task.managerId.empty}")
    @Min(value = 1, message = "{task.managerId.min}")
    private Integer managerId;

    public BaseTaskPermissionDTO() {
    }

    public BaseTaskPermissionDTO(Integer taskId, Integer managerId) {
        this.taskId = taskId;
        this.managerId = managerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

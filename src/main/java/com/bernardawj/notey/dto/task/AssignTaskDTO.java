package com.bernardawj.notey.dto.task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AssignTaskDTO extends BaseTaskPermissionDTO {

    @NotNull(message = "{task.userId.empty}")
    @Min(value = 1, message = "{task.userId.min}")
    private Integer userId;

    @NotNull(message = "{task.assign.empty}")
    private Boolean assign;

    public AssignTaskDTO() {
    }

    public AssignTaskDTO(Integer taskId, Integer managerId, Integer userId, Boolean assign) {
        super(taskId, managerId);
        this.userId = userId;
        this.assign = assign;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getAssign() {
        return assign;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
    }
}

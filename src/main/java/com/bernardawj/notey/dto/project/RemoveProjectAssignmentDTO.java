package com.bernardawj.notey.dto.project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RemoveProjectAssignmentDTO extends BaseProjectPermissionDTO {

    @NotNull(message = "{project.userId.empty}")
    @Min(value = 1, message = "{project.userId.min}")
    private Integer userId;

    public RemoveProjectAssignmentDTO() {
    }

    public RemoveProjectAssignmentDTO(Integer projectId, Integer managerId, Integer userId) {
        super(projectId, managerId);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

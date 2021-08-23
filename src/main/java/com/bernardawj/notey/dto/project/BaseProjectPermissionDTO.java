package com.bernardawj.notey.dto.project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class BaseProjectPermissionDTO {

    @NotNull(message = "{project.id.empty}")
    @Min(value = 1, message = "{project.id.min}")
    private Integer projectId;

    @NotNull(message = "{project.managerId.empty}")
    @Min(value = 1, message = "{project.managerId.min}")
    private Integer managerId;

    public BaseProjectPermissionDTO() {
    }

    public BaseProjectPermissionDTO(Integer projectId, Integer managerId) {
        this.projectId = projectId;
        this.managerId = managerId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

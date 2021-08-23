package com.bernardawj.notey.dto.project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProjectAcceptanceDTO {

    @NotNull(message = "{project.userId.empty}")
    @Min(value = 1, message = "{project.userId.min}")
    private Integer userId;

    @NotNull(message = "{project.id.empty}")
    @Min(value = 1, message = "{project.id.min}")
    private Integer projectId;

    private Boolean accept;

    public ProjectAcceptanceDTO() {
    }

    public ProjectAcceptanceDTO(Integer userId, Integer projectId, Boolean accept) {
        this.userId = userId;
        this.projectId = projectId;
        this.accept = accept;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}

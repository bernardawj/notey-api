package com.bernardawj.notey.dto.project;

public class ProjectAcceptanceDTO {

    private Integer userId;
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

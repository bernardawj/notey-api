package com.bernardawj.notey.dto.project;

public class AssignProjectDTO {

    private Integer projectId;
    private Integer userId;

    public AssignProjectDTO(Integer projectId, Integer userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

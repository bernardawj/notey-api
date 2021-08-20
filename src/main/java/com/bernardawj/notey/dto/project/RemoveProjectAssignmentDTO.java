package com.bernardawj.notey.dto.project;

public class RemoveProjectAssignmentDTO {

    private Integer projectId;
    private Integer userId;
    private Integer managerId;

    public RemoveProjectAssignmentDTO() {
    }

    public RemoveProjectAssignmentDTO(Integer projectId, Integer userId, Integer managerId) {
        this.projectId = projectId;
        this.userId = userId;
        this.managerId = managerId;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

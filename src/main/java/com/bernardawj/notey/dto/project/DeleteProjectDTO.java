package com.bernardawj.notey.dto.project;

public class DeleteProjectDTO {

    private Integer projectId;
    private Integer managerId;

    public DeleteProjectDTO() {
    }

    public DeleteProjectDTO(Integer projectId, Integer managerId) {
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

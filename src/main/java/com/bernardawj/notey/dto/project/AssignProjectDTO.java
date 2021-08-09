package com.bernardawj.notey.dto.project;

public class AssignProjectDTO {

    private Integer projectId;
    private String email;

    public AssignProjectDTO(Integer projectId, String email) {
        this.projectId = projectId;
        this.email = email;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

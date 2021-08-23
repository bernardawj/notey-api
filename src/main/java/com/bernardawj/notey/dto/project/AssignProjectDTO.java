package com.bernardawj.notey.dto.project;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AssignProjectDTO extends BaseProjectPermissionDTO {

    @NotEmpty(message = "{project.email.empty}")
    @Email(message = "{project.email.invalid}")
    private String email;

    public AssignProjectDTO() {
    }

    public AssignProjectDTO(Integer projectId, Integer managerId, String email) {
        super(projectId, managerId);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

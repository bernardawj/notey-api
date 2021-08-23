package com.bernardawj.notey.dto.project;

public class DeleteProjectDTO extends BaseProjectPermissionDTO {

    public DeleteProjectDTO(Integer projectId, Integer managerId) {
        super(projectId, managerId);
    }
}

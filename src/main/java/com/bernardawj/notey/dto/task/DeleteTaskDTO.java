package com.bernardawj.notey.dto.task;

public class DeleteTaskDTO extends BaseTaskPermissionDTO {

    public DeleteTaskDTO() {
    }

    public DeleteTaskDTO(Integer taskId, Integer managerId) {
        super(taskId, managerId);
    }
}

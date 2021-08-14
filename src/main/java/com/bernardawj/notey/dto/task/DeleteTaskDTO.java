package com.bernardawj.notey.dto.task;

public class DeleteTaskDTO {

    private Integer taskId;
    private Integer managerId;

    public DeleteTaskDTO() {
    }

    public DeleteTaskDTO(Integer taskId, Integer managerId) {
        this.taskId = taskId;
        this.managerId = managerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

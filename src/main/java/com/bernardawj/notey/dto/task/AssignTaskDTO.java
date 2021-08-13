package com.bernardawj.notey.dto.task;

public class AssignTaskDTO {

    private Integer taskId;
    private Integer userId;
    private Integer managerId;
    private Boolean assign;

    public AssignTaskDTO() {
    }

    public AssignTaskDTO(Integer taskId, Integer userId, Integer managerId, Boolean assign) {
        this.taskId = taskId;
        this.userId = userId;
        this.managerId = managerId;
        this.assign = assign;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public Boolean getAssign() {
        return assign;
    }

    public void setAssign(Boolean assign) {
        this.assign = assign;
    }
}

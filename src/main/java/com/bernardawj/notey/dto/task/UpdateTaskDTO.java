package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.enums.TaskType;

import java.time.LocalDateTime;

public class UpdateTaskDTO {

    private Integer taskId;
    private String name;
    private String description;
    private TaskType type;
    private Boolean completed;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private Integer managerId;

    public UpdateTaskDTO() {
    }

    public UpdateTaskDTO(Integer taskId, String name, String description, TaskType type, Boolean completed,
                         LocalDateTime startAt, LocalDateTime endAt, Integer managerId) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.completed = completed;
        this.startAt = startAt;
        this.endAt = endAt;
        this.managerId = managerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.enums.TaskType;

import java.time.LocalDateTime;

public class CreateTaskDTO {

    private Integer id;
    private String name;
    private String description;
    private TaskType type;
    private Boolean isCompleted;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;

    private Integer projectId;

    public CreateTaskDTO() {
    }

    public CreateTaskDTO(Integer id, String name, String description, TaskType type, Boolean isCompleted,
                         LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt, Integer projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.isCompleted = isCompleted;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}

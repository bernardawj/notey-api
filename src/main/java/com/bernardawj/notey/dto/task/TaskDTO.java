package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.enums.TaskType;

import java.time.LocalDateTime;

public class TaskDTO {

    private Integer id;
    private String name;
    private String description;
    private TaskType type;
    private Boolean isCompleted;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
    private ProjectDTO project;
    private UserDTO user;

    public TaskDTO() {
    }

    public TaskDTO(Integer id, String name, String description, TaskType type, Boolean isCompleted,
                   LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt) {
        this(id, name, description, type, isCompleted, startAt, endAt, createdAt, null, null);
    }

    public TaskDTO(Integer id, String name, String description, TaskType type, Boolean isCompleted,
                   LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt, ProjectDTO project) {
        this(id, name, description, type, isCompleted, startAt, endAt, createdAt, project, null);
    }

    public TaskDTO(Integer id, String name, String description, TaskType type, Boolean isCompleted,
                   LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt, ProjectDTO project,
                   UserDTO user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.isCompleted = isCompleted;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.project = project;
        this.user = user;
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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

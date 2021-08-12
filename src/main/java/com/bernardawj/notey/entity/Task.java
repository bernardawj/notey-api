package com.bernardawj.notey.entity;

import com.bernardawj.notey.enums.TaskType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "project_id", unique = true)
//    private Project project;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", unique = true)
//    private User user;

    public Task(Integer id, String name, String description, TaskType type, Boolean isCompleted,
                LocalDateTime createdAt, LocalDateTime endedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
}

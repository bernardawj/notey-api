package com.bernardawj.notey.dto.project;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class BaseProjectDTO {

    @NotNull(message = "{project.name}")
    private String name;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private Integer managerId;

    public BaseProjectDTO() {
    }

    public BaseProjectDTO(String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                          Integer managerId) {
        this.name = name;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.managerId = managerId;
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

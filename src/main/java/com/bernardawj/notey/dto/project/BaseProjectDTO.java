package com.bernardawj.notey.dto.project;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class BaseProjectDTO {

    @NotBlank(message = "{project.name.empty}")
    @Length(max = 50, message = "project.name.length")
    private String name;

    @NotBlank(message = "{project.description.empty}")
    @Length(max = 255, message = "project.name.length")
    private String description;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @NotNull(message = "{project.managerId.empty}")
    @Min(value = 1, message = "{project.managerId.min}")
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

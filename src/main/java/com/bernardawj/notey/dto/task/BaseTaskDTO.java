package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.enums.TaskType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class BaseTaskDTO {

    @NotEmpty(message = "{task.name.empty}")
    @Length(max = 50, message = "{task.name.length}")
    private String name;

    @NotEmpty(message = "{task.description.empty}")
    @Length(max = 255, message = "{task.description.length}")
    private String description;

    @NotNull(message = "{task.type.empty}")
    private TaskType type;

    @NotNull(message = "{task.startAt.empty}")
    private LocalDateTime startAt;

    @NotNull(message = "{task.endAt.empty}")
    private LocalDateTime endAt;

    public BaseTaskDTO() {
    }

    public BaseTaskDTO(String name, String description, TaskType type, LocalDateTime startAt, LocalDateTime endAt) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.startAt = startAt;
        this.endAt = endAt;
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
}

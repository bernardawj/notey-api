package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.enums.TaskType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UpdateTaskDTO extends BaseTaskDTO {

    @NotNull(message = "{task.id.empty}")
    @Min(value = 1, message = "{task.id.length}")
    private Integer taskId;

    @NotNull(message = "{task.completed.empty}")
    private Boolean completed;

    @NotNull(message = "{task.managerId.empty}")
    @Min(value = 1, message = "{task.managerId.length}")
    private Integer managerId;

    public UpdateTaskDTO() {
    }

    public UpdateTaskDTO(String name, String description, TaskType type, LocalDateTime startAt, LocalDateTime endAt,
                         Integer taskId, Boolean completed, Integer managerId) {
        super(name, description, type, startAt, endAt);
        this.taskId = taskId;
        this.completed = completed;
        this.managerId = managerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

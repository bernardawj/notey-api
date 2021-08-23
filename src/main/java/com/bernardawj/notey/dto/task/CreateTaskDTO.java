package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.enums.TaskType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateTaskDTO extends BaseTaskDTO {

    @NotNull(message = "{task.projectId.empty}")
    @Min(value = 1, message = "{task.projectId.min}")
    private Integer projectId;

    public CreateTaskDTO() {
    }

    public CreateTaskDTO(String name, String description, TaskType type, LocalDateTime startAt, LocalDateTime endAt,
                         Integer projectId) {
        super(name, description, type, startAt, endAt);
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}

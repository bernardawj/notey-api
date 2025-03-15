package com.bernardawj.notey.dto.shared.filter;

import com.bernardawj.notey.enums.TaskType;

public class TaskFilterDTO extends FilterDTO {

    private TaskType type;
    private Boolean completed;

    public TaskFilterDTO() {
    }

    public TaskFilterDTO(String searchString, TaskType type, Boolean completed) {
        super(searchString);
        this.type = type;
        this.completed = completed;
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
}

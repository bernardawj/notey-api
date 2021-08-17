package com.bernardawj.notey.dto.task;

import com.bernardawj.notey.dto.shared.PaginationDTO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class TaskListDTO {

    private List<TaskDTO> tasks;
    private PaginationDTO pagination;

    public TaskListDTO() {
    }

    public TaskListDTO(List<TaskDTO> tasks, PaginationDTO pagination) {
        this.tasks = tasks;
        this.pagination = pagination;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}

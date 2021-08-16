package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private UserDTO manager;
    private List<ProjectUserDTO> assignedUsers;
    private List<TaskDTO> tasks;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                      UserDTO manager) {
        this(id, name, description, startAt, endAt, manager, null, null);
    }

    public ProjectDTO(Integer id, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                      UserDTO manager, List<ProjectUserDTO> assignedUsers, List<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.manager = manager;
        this.assignedUsers = assignedUsers;
        this.tasks = tasks;
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

    public UserDTO getManager() {
        return manager;
    }

    public void setManager(UserDTO manager) {
        this.manager = manager;
    }

    public List<ProjectUserDTO> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<ProjectUserDTO> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}

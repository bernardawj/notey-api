package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime accessedAt;

    private UserDTO manager;
    private List<ProjectUserDTO> assignedUsers;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                      LocalDateTime accessedAt, UserDTO manager) {
        this(id, name, description, startAt, endAt, accessedAt, manager, null);
    }

    public ProjectDTO(Integer id, String name, String description, LocalDateTime startAt, LocalDateTime endAt,
                      LocalDateTime accessedAt, UserDTO manager, List<ProjectUserDTO> assignedUsers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.accessedAt = accessedAt;
        this.manager = manager;
        this.assignedUsers = assignedUsers;
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

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

    public List<ProjectUserDTO> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<ProjectUserDTO> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}

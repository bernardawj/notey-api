package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.user.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime accessedAt;

    private UserDTO manager;
    private List<ProjectUserDTO> assignedUsers;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, String name, String description, LocalDate startAt, LocalDate endAt,
                      LocalDateTime accessedAt, UserDTO manager) {
        this(id, name, description, startAt, endAt, accessedAt, manager, null);
    }

    public ProjectDTO(Integer id, String name, String description, LocalDate startAt, LocalDate endAt,
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

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDate endAt) {
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

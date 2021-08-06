package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private LocalDate startAt;
    private LocalDate endAt;

    private UserDTO manager;
    private List<UserDTO> users;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer id, String name, String description, LocalDate startAt, LocalDate endAt,
                      UserDTO manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.manager = manager;
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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
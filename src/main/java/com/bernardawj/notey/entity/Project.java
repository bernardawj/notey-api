package com.bernardawj.notey.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDateTime accessedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", unique = true)
    private User manager;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", unique = true)
    private List<ProjectUser> projectUsers;

    public Project() {
    }

    public Project(String name, String description, LocalDate startAt, LocalDate endAt,
                   LocalDateTime accessedAt, User manager) {
        this(name, description, startAt, endAt, accessedAt, manager, new ArrayList<>());
    }

    public Project(String name, String description, LocalDate startAt, LocalDate endAt,
                   LocalDateTime accessedAt, User manager, List<ProjectUser> projectUsers) {
        this.name = name;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.accessedAt = accessedAt;
        this.manager = manager;
        this.projectUsers = projectUsers;
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

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<ProjectUser> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(List<ProjectUser> projectUsers) {
        this.projectUsers = projectUsers;
    }
}

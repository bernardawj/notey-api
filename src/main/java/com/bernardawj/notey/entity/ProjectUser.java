package com.bernardawj.notey.entity;

import com.bernardawj.notey.utility.ProjectUserCompositeKey;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_users")
@IdClass(ProjectUserCompositeKey.class)
public class ProjectUser {

    @Id
    @Column(name = "project_id")
    private Integer projectId;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    private Boolean hasAccepted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", unique = true, insertable = false, updatable = false)
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, insertable = false, updatable = false)
    private User user;

    public ProjectUser() {
    }

    public ProjectUser(Integer projectId, Integer userId, Boolean hasAccepted, List<Task> tasks) {
        this.projectId = projectId;
        this.userId = userId;
        this.hasAccepted = hasAccepted;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getHasAccepted() {
        return hasAccepted;
    }

    public void setHasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

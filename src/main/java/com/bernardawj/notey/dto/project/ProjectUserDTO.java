package com.bernardawj.notey.dto.project;

public class ProjectUserDTO {

    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean hasAccepted;

    public ProjectUserDTO() {
    }

    public ProjectUserDTO(Integer userId, String email, String firstName, String lastName, Boolean hasAccepted) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hasAccepted = hasAccepted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getHasAccepted() {
        return hasAccepted;
    }

    public void setHasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
    }
}

package com.bernardawj.notey.dto;

import com.bernardawj.notey.dto.project.ProjectDTO;

import java.util.List;

public class UserDTO {

    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private List<ProjectDTO> assignedProjects;
    private List<ProjectDTO> managedProjects;

    public UserDTO() {
    }

    public UserDTO(Integer id) {
        this.id = id;
    }

    public UserDTO(Integer id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(Integer id, String email, String password, String firstName, String lastName,
                   List<ProjectDTO> assignedProjects) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assignedProjects = assignedProjects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<ProjectDTO> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<ProjectDTO> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public List<ProjectDTO> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(List<ProjectDTO> managedProjects) {
        this.managedProjects = managedProjects;
    }
}

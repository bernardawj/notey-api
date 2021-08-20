package com.bernardawj.notey.dto.project;

import com.bernardawj.notey.dto.shared.PaginationDTO;

import java.util.List;

public class ProjectListDTO {

    private List<ProjectDTO> projects;
    private PaginationDTO pagination;

    public ProjectListDTO() {
    }

    public ProjectListDTO(List<ProjectDTO> projects, PaginationDTO pagination) {
        this.projects = projects;
        this.pagination = pagination;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}

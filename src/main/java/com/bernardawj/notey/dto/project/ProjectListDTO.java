package com.bernardawj.notey.dto.project;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ProjectListDTO {

    private List<ProjectDTO> projects;
    private PageRequest pageRequest;

    public ProjectListDTO() {
    }

    public ProjectListDTO(List<ProjectDTO> projects, PageRequest pageRequest) {
        this.projects = projects;
        this.pageRequest = pageRequest;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
}

package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllManagedProjects(Integer managerId) throws UserServiceException;

    List<ProjectDTO> getAllAssignedProjects(Integer userId) throws UserServiceException;

    List<ProjectDTO> getRecentlyAccessedProjects(Integer userId, Integer count) throws UserServiceException;

    void assignUserToProject(Integer projectId, String email) throws ProjectServiceException, UserServiceException;

    ProjectDTO getProject(Integer projectId) throws ProjectServiceException;

    ProjectDTO addProject(ProjectDTO projectDTO) throws UserServiceException, ProjectServiceException;

    ProjectDTO updateProject(ProjectDTO updateProjectDTO) throws ProjectServiceException;

    void deleteProject(Integer projectId, Integer managerId) throws ProjectServiceException;
}

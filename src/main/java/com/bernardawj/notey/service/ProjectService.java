package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.CreateProjectDTO;
import com.bernardawj.notey.dto.project.DeleteProjectDTO;
import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.dto.project.UpdateProjectDTO;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllManagedProjects(Integer managerId) throws UserServiceException;

    List<ProjectDTO> getAllAssignedProjects(Integer userId) throws UserServiceException;

    List<ProjectDTO> getRecentlyAccessedProjects(Integer userId, Integer count) throws UserServiceException;

    void assignUserToProject(Integer projectId, String email) throws ProjectServiceException, UserServiceException;

    ProjectDTO getProject(Integer projectId) throws ProjectServiceException;

    ProjectDTO createProject(CreateProjectDTO createProjectDTO) throws UserServiceException, ProjectServiceException;

    ProjectDTO updateProject(UpdateProjectDTO updateProjectDTO) throws ProjectServiceException;

    void deleteProject(DeleteProjectDTO deleteProjectDTO) throws ProjectServiceException;
}

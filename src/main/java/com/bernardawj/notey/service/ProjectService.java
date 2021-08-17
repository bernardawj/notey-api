package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.dto.user.ProjectAcceptanceDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllManagedProjects(Integer managerId) throws UserServiceException;

    List<ProjectDTO> getAllAssignedProjects(Integer userId) throws UserServiceException;

    void assignUserToProject(AssignProjectDTO assignProjectDTO) throws ProjectServiceException, NotificationServiceException;

    void updateProjectAcceptance(ProjectAcceptanceDTO projectAcceptanceDTO) throws ProjectServiceException;

    ProjectDTO getProject(Integer projectId, Integer userId) throws ProjectServiceException;

    ProjectDTO createProject(CreateProjectDTO createProjectDTO) throws UserServiceException, ProjectServiceException;

    ProjectDTO updateProject(UpdateProjectDTO updateProjectDTO) throws ProjectServiceException;

    void deleteProject(DeleteProjectDTO deleteProjectDTO) throws ProjectServiceException;
}

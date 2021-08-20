package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.dto.user.ProjectAcceptanceDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;

public interface ProjectService {

    ProjectListDTO getAllManagedProjects(GetManagedProjectDTO getManagedProjectDTO) throws UserServiceException;

    ProjectListDTO getAllAssignedProjects(GetAssignedProjectDTO getAssignedProjectDTO) throws UserServiceException;

    void assignUserToProject(AssignProjectDTO assignProjectDTO) throws ProjectServiceException,
            NotificationServiceException;

    void removeUserFromProject(RemoveProjectAssignmentDTO removeProjectAssignmentDTO) throws ProjectServiceException,
            NotificationServiceException;

    void updateProjectAcceptance(ProjectAcceptanceDTO projectAcceptanceDTO) throws ProjectServiceException,
            NotificationServiceException;

    ProjectDTO getProject(Integer projectId, Integer userId) throws ProjectServiceException;

    ProjectDTO createProject(CreateProjectDTO createProjectDTO) throws UserServiceException, ProjectServiceException;

    ProjectDTO updateProject(UpdateProjectDTO updateProjectDTO) throws ProjectServiceException;

    void deleteProject(Integer projectId, Integer managerId) throws ProjectServiceException;
}

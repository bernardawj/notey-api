package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;
    private final UserService userService;

    private final String PROJECT_NOT_FOUND = "ProjectService.PROJECT_NOT_FOUND";
    private final String INVALID_PROJECT_DATES = "ProjectService.INVALID_PROJECT_DATES";
    private final String USER_IS_MANAGER = "ProjectService.USER_IS_MANAGER";
    private final String USER_NOT_FOUND = "ProjectService.USER_NOT_FOUND";

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository,
                              ProjectUserRepository projectUserRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUserRepository = projectUserRepository;
        this.userService = userService;
    }

    @Override
    public List<ProjectDTO> getAllManagedProjects(Integer managerId) throws UserServiceException {
        // Check if manager exists
        UserDTO userDTO = this.userService.getUserDetails(managerId);

        // Retrieve all projects based on the manager ID
        Iterable<Project> projects = this.projectRepository.findProjectsByManagerId(userDTO.getId());

        // Pack into DTO list and return
        return populateProjectsDTO(userDTO, projects);
    }

    @Override
    public List<ProjectDTO> getAllAssignedProjects(Integer userId) throws UserServiceException {
        // Check if user exists
        UserDTO userDTO = this.userService.getUserDetails(userId);

        return userDTO.getAssignedProjects();
    }

    @Override
    public List<ProjectDTO> getRecentlyAccessedProjects(Integer userId, Integer count) throws UserServiceException {
        // Check if user exists
        UserDTO userDTO = this.userService.getUserDetails(userId);

        // Get recently accessed projects
        Iterable<Project> projects = this.projectRepository.findRecentlyAccessedProjects(userDTO.getId());

        // Pack into DTO list and return
        return populateProjectsDTO(userDTO, projects);
    }

    @Override
    public void assignUserToProject(AssignProjectDTO assignProjectDTO) throws ProjectServiceException {
        // Check if project exists
        Optional<Project> optProject = this.projectRepository.findById(assignProjectDTO.getProjectId());
        Project project = optProject.orElseThrow(() -> new ProjectServiceException(PROJECT_NOT_FOUND));

        // Check if user exists
        Optional<User> optUser = this.userRepository.findUserByEmail(assignProjectDTO.getEmail());
        User user = optUser.orElseThrow(() -> new ProjectServiceException(USER_NOT_FOUND));

        // Check if user is the manager
        if (project.getManager().getId().intValue() == user.getId().intValue())
            throw new ProjectServiceException(USER_IS_MANAGER);

        // Update project and save to database
        project.getProjectUsers().add(new ProjectUser(project.getId(), user.getId(), false, null));
        this.projectRepository.save(project);
    }

    @Override
    public ProjectDTO getProject(Integer projectId, Integer userId) throws ProjectServiceException {
        // Check if project exists
        Optional<Project> optProject = this.projectRepository.findProjectByProjectIdAndManagerIdOrUserId(projectId,
                userId);
        Project project = optProject.orElseThrow(() -> new ProjectServiceException(PROJECT_NOT_FOUND));

        return populateProjectDTO(project);
    }

    @Override
    public ProjectDTO createProject(CreateProjectDTO createProjectDTO) throws ProjectServiceException {
        // Check if manager exists
        Optional<User> optUser = this.userRepository.findById(createProjectDTO.getManagerId());
        User user = optUser.orElseThrow(() -> new ProjectServiceException(USER_NOT_FOUND));

        // Validate project dates
        if (createProjectDTO.getStartAt().isAfter(createProjectDTO.getEndAt()))
            throw new ProjectServiceException(INVALID_PROJECT_DATES);

        // Save to database
        Project project = new Project(createProjectDTO.getName(), createProjectDTO.getDescription(),
                createProjectDTO.getStartAt(), createProjectDTO.getEndAt(), LocalDateTime.now(ZoneOffset.UTC), user);
        this.projectRepository.save(project);

        return populateProjectDTO(project);
    }

    @Override
    public ProjectDTO updateProject(UpdateProjectDTO updateProjectDTO) throws ProjectServiceException {
        // Check if project exists
        Optional<Project> optProject =
                this.projectRepository.findProjectByProjectIdAndManagerId(updateProjectDTO.getId(),
                        updateProjectDTO.getManagerId());
        Project project = optProject.orElseThrow(() -> new ProjectServiceException(PROJECT_NOT_FOUND));

        // Validate project dates
        if (updateProjectDTO.getStartAt().isAfter(updateProjectDTO.getEndAt()))
            throw new ProjectServiceException(INVALID_PROJECT_DATES);

        // Update project
        project.setName(updateProjectDTO.getName());
        project.setDescription(updateProjectDTO.getDescription());
        project.setStartAt(updateProjectDTO.getStartAt());
        project.setEndAt(updateProjectDTO.getEndAt());
        this.projectRepository.save(project);

        UserDTO manager = new UserDTO(project.getManager().getId(), project.getManager().getEmail(), null,
                project.getManager().getFirstName(), project.getManager().getLastName());
        return new ProjectDTO(project.getId(), project.getName(), project.getDescription(), project.getStartAt(),
                project.getEndAt(), project.getAccessedAt(), manager);
    }

    @Override
    public void deleteProject(DeleteProjectDTO deleteProjectDTO) throws ProjectServiceException {
        // Check if project exists
        Optional<Project> optProject =
                this.projectRepository.findProjectByProjectIdAndManagerId(deleteProjectDTO.getProjectId(),
                        deleteProjectDTO.getManagerId());
        Project project = optProject.orElseThrow(() -> new ProjectServiceException(PROJECT_NOT_FOUND));

        // Delete project
        this.projectRepository.deleteById(project.getId());
    }

    private ProjectDTO populateProjectDTO(Project project) {
        // Populate manager DTO
        UserDTO managerDTO = new UserDTO(project.getManager().getId(), project.getManager().getEmail(), null,
                project.getManager().getFirstName(), project.getManager().getLastName());

        // Populate project DTO
        ProjectDTO projectDTO = new ProjectDTO(project.getId(), project.getName(), project.getDescription(),
                project.getStartAt(), project.getEndAt(), project.getAccessedAt(), managerDTO, null);

        // Populate assigned users DTO
        List<ProjectUserDTO> assignedUsersDTO = new ArrayList<>();
        project.getProjectUsers().forEach(projectUser -> {
            assignedUsersDTO.add(new ProjectUserDTO(projectUser.getUser().getId(), projectUser.getUser().getEmail(),
                    projectUser.getUser().getFirstName(), projectUser.getUser().getLastName(),
                    projectUser.getHasAccepted()));
        });

        projectDTO.setAssignedUsers(assignedUsersDTO);

        return projectDTO;
    }

    private List<ProjectDTO> populateProjectsDTO(UserDTO userDTO, Iterable<Project> projects) {
        List<ProjectDTO> projectsDTO = new ArrayList<>();

        projects.forEach(project -> {
            ProjectDTO projectDTO = populateProjectDTO(project);
            projectsDTO.add(projectDTO);
        });

        return projectsDTO;
    }
}

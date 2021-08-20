package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.dto.shared.PaginationDTO;
import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.dto.user.ProjectAcceptanceDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.enums.NotificationType;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.TaskRepository;
import com.bernardawj.notey.repository.UserRepository;
import com.bernardawj.notey.utility.ProjectUserCompositeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    private final String PROJECT_NOT_FOUND = "ProjectService.PROJECT_NOT_FOUND";
    private final String INVALID_PROJECT_DATES = "ProjectService.INVALID_PROJECT_DATES";
    private final String USER_IS_MANAGER = "ProjectService.USER_IS_MANAGER";
    private final String USER_NOT_FOUND = "ProjectService.USER_NOT_FOUND";

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository,
                              ProjectUserRepository projectUserRepository, TaskRepository taskRepository,
                              UserService userService, NotificationService notificationService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUserRepository = projectUserRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public ProjectListDTO getAllManagedProjects(GetManagedProjectDTO getManagedProjectDTO) throws UserServiceException {
        // Check if manager exists
        UserDTO userDTO = this.userService.getUserDetails(getManagedProjectDTO.getManagerId());

        // Retrieve all projects based on the manager ID
        Pageable pageable = PageRequest.of(getManagedProjectDTO.getInputPage().getPageNo() - 1,
                getManagedProjectDTO.getInputPage().getPageSize());
        Page<Project> projects = this.projectRepository.findProjectsByManagerId(userDTO.getId(),
                getManagedProjectDTO.getFilter().getSearchString(), pageable);

        // Pack into DTO list and return
        return populateProjectListDTO(projects, getManagedProjectDTO.getInputPage().getPageNo(),
                projects.getTotalPages());
    }

    @Override
    public ProjectListDTO getAllAssignedProjects(GetAssignedProjectDTO getAssignedProjectDTO) throws UserServiceException {
        // Check if user exists
        UserDTO userDTO = this.userService.getUserDetails(getAssignedProjectDTO.getUserId());

        // Retrieve all projects based on user ID
        Pageable pageable = PageRequest.of(getAssignedProjectDTO.getInputPage().getPageNo() - 1,
                getAssignedProjectDTO.getInputPage().getPageSize());
        Page<Project> projects = this.projectRepository.findProjectsByUserId(userDTO.getId(),
                getAssignedProjectDTO.getFilter().getSearchString(), pageable);

        // Pack into DTO list and return
        return populateProjectListDTO(projects, getAssignedProjectDTO.getInputPage().getPageNo(),
                projects.getTotalPages());
    }

    @Override
    public void assignUserToProject(AssignProjectDTO assignProjectDTO) throws ProjectServiceException,
            NotificationServiceException {
        // Check if project exists
        Optional<Project> optProject = this.projectRepository.findById(assignProjectDTO.getProjectId());
        Project project = optProject.orElseThrow(() -> new ProjectServiceException(PROJECT_NOT_FOUND));

        // Check if user exists
        Optional<User> optUser = this.userRepository.findUserByEmail(assignProjectDTO.getEmail());
        User user = optUser.orElseThrow(() -> new ProjectServiceException(USER_NOT_FOUND));

        // Check if user is already in the project
        Optional<ProjectUser> optProjectUser = this.projectUserRepository.findByProjectIdAndUserId(project.getId(),
                user.getId());
        if (optProjectUser.isPresent())
            throw new ProjectServiceException("ProjectService.USER_EXISTS_IN_PROJECT");

        // Check if user is the manager
        if (project.getManager().getId().intValue() == user.getId().intValue())
            throw new ProjectServiceException(USER_IS_MANAGER);

        // Update project and save to database
        project.getProjectUsers().add(new ProjectUser(project.getId(), user.getId(), false, null));
        this.projectRepository.save(project);

        // Create notification and save to database
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();
        createNotificationDTO.setMessage(String.format("Project (%s) has been assigned to you by %s %s.",
                project.getName(),
                project.getManager().getFirstName(), project.getManager().getLastName()));
        createNotificationDTO.setType(NotificationType.PROJECT_INVITATION);
        createNotificationDTO.setFromUserId(project.getManager().getId());
        createNotificationDTO.setToUserId(user.getId());
        this.notificationService.createNotification(createNotificationDTO);
    }

    @Override
    public void removeUserFromProject(RemoveProjectAssignmentDTO removeProjectAssignmentDTO) throws ProjectServiceException,
            NotificationServiceException {
        // Check if user is already in the project
        Optional<ProjectUser> optProjectUser =
                this.projectUserRepository.findByProjectIdAndUserId(removeProjectAssignmentDTO.getProjectId(),
                        removeProjectAssignmentDTO.getUserId());
        ProjectUser projectUser = optProjectUser.orElseThrow(() -> new ProjectServiceException("ProjectService" +
                ".USER_NOT_FOUND_IN_PROJECT"));

        // Check if manager ID is valid
        boolean isManager =
                projectUser.getProject().getManager().getId().intValue() == removeProjectAssignmentDTO.getManagerId();
        if (!isManager)
            throw new ProjectServiceException("ProjectService.USER_NOT_MANAGER");

        // Remove all tasks associated with the user
        List<Task> userTasks =
                projectUser.getUser().getTasks().stream().filter(task ->
                        task.getProject().getId().intValue() == projectUser.getProjectId().intValue()).collect(Collectors.toList());
        userTasks.forEach(task -> task.setUser(null));

        // Remove project assignment
        this.projectUserRepository.deleteById(new ProjectUserCompositeKey(projectUser.getProjectId(),
                projectUser.getUserId()));

        // Create notification
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();
        createNotificationDTO.setMessage(String.format("You have been removed from Project (%s) by %s %s.",
                projectUser.getProject().getName(), projectUser.getProject().getManager().getFirstName(),
                projectUser.getProject().getManager().getLastName()));
        createNotificationDTO.setType(NotificationType.PROJECT_REMOVAL);
        createNotificationDTO.setFromUserId(projectUser.getProject().getManager().getId());
        createNotificationDTO.setToUserId(projectUser.getUser().getId());
        this.notificationService.createNotification(createNotificationDTO);
    }

    @Override
    public void updateProjectAcceptance(ProjectAcceptanceDTO projectAcceptanceDTO) throws ProjectServiceException {
        // Check if user and project exists within the database
        ProjectUserCompositeKey compositeKey = new ProjectUserCompositeKey(projectAcceptanceDTO.getProjectId(),
                projectAcceptanceDTO.getUserId());
        Optional<ProjectUser> optProjectUser =
                this.projectUserRepository.findById(compositeKey);
        ProjectUser projectUser = optProjectUser.orElseThrow(() -> new ProjectServiceException("ProjectService" +
                ".NO_PROJECT_FOR_ACCEPTANCE"));

        // Delete project user if declined
        if (!projectAcceptanceDTO.getAccept()) {
            this.projectUserRepository.deleteById(compositeKey);
            return;
        }

        // Perform acceptance on project and update the database
        projectUser.setHasAccepted(true);
        this.projectUserRepository.save(projectUser);
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
                createProjectDTO.getStartAt(), createProjectDTO.getEndAt(), user);
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
                project.getEndAt(), manager);
    }

    @Override
    public void deleteProject(Integer projectId, Integer managerId) throws ProjectServiceException {
        // Check if project exists
        Optional<Project> optProject =
                this.projectRepository.findProjectByProjectIdAndManagerId(projectId, managerId);
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
                project.getStartAt(), project.getEndAt(), managerDTO, null, null);

        // Populate assigned users DTO
        List<ProjectUserDTO> assignedUsersDTO = new ArrayList<>();
        project.getProjectUsers().forEach(projectUser -> {
            assignedUsersDTO.add(new ProjectUserDTO(projectUser.getUser().getId(), projectUser.getUser().getEmail(),
                    projectUser.getUser().getFirstName(), projectUser.getUser().getLastName(),
                    projectUser.getHasAccepted()));
        });

        // Populate tasks DTO
        List<TaskDTO> tasksDTO = new ArrayList<>();
        project.getTasks().forEach(task -> {
            tasksDTO.add(new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getType(),
                    task.getCompleted(), task.getStartAt(), task.getEndAt(), task.getCreatedAt()));
        });

        projectDTO.setAssignedUsers(assignedUsersDTO);
        projectDTO.setTasks(tasksDTO);

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

    private ProjectListDTO populateProjectListDTO(Iterable<Project> projects, Integer pageNo, Integer totalPages) {
        ProjectListDTO projectListDTO = new ProjectListDTO();
        projectListDTO.setProjects(populateProjectsDTO(null, projects));
        projectListDTO.setPagination(new PaginationDTO(pageNo, totalPages));
        return projectListDTO;
    }
}

package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.UserRepository;
import com.bernardawj.notey.utility.ProjectUserCompositeKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectUserRepository projectUserRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    // region Assign User

    @Test
    public void invalidProjectIdOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 1, "dummy@email.com");

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidManagerIdOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 1, "dummy@email.com");

        // Mock entity
        User manager = new User();
        manager.setId(2);

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.NOT_MATCHING_MANAGER", ex.getMessage());
    }

    @Test
    public void invalidEmailOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 1, "dummy@email.com");

        // Mock entity
        User manager = new User();
        manager.setId(1);

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.USER_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidUserExistsInProjectOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 1, "dummy@email.com");

        // Mock entity
        User user = new User();
        user.setId(1);

        User manager = new User();
        manager.setId(1);

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.of(new ProjectUser()));

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.USER_EXISTS_IN_PROJECT", ex.getMessage());
    }

    @Test
    public void invalidAssignedUserIsManagerOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 1, "dummy@email.com");

        // Mock entity
        User user = new User();
        user.setId(1);

        User manager = new User();
        manager.setId(1);

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.ASSIGNED_USER_IS_MANAGER", ex.getMessage());
    }

    @Test
    public void validOnAssignUserToProject() throws ProjectServiceException, NotificationServiceException {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, 2, "user@email.com");

        // Mock entity
        User user = new User();
        user.setId(1);

        User manager = new User();
        manager.setId(2);

        ProjectUser projectUser = new ProjectUser(1, 1, false, new HashSet<>());

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);
        project.setProjectUsers(new HashSet<>());
        project.getProjectUsers().add(projectUser);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(this.projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        Mockito.doNothing().when(this.notificationService).createNotification(Mockito.any(CreateNotificationDTO.class));

        // Call service method
        this.projectService.assignUserToProject(assignProjectDTO);

        // Check if any of the assertions fail
        boolean hasProjectUser = project.getProjectUsers().stream().anyMatch(pu ->
                pu.getProjectId().intValue() == project.getId().intValue() && pu.getUserId() == user.getId().intValue());
        Assertions.assertTrue(hasProjectUser);
    }

    // endregion

    // region Remove User From Project

    @Test
    public void invalidProjectIdAndUserId_OnRemoveUserFromProject_ShouldThrowException() {
        // Mock DTO
        RemoveProjectAssignmentDTO removeProjectAssignmentDTO = new RemoveProjectAssignmentDTO(1, 1, 2);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.removeUserFromProject(removeProjectAssignmentDTO));

        // Check if exception message is same
        Assertions.assertEquals("ProjectService.USER_NOT_FOUND_IN_PROJECT", ex.getMessage());
    }

    @Test
    public void invalidManagerId_OnRemoveUserFromProject_ShouldThrowException() {
        // Mock DTO
        RemoveProjectAssignmentDTO removeProjectAssignmentDTO = new RemoveProjectAssignmentDTO(1, 1, 2);

        // Mock entity
        User manager = new User();
        manager.setId(2);

        Project project = new Project();
        project.setManager(manager);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.of(projectUser));

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.removeUserFromProject(removeProjectAssignmentDTO));

        // Check if exception message is same
        Assertions.assertEquals("ProjectService.NOT_MATCHING_MANAGER", ex.getMessage());
    }

    @Test
    public void valid_OnRemoveUserFromProject() throws ProjectServiceException, NotificationServiceException {
        // Mock DTO
        RemoveProjectAssignmentDTO removeProjectAssignmentDTO = new RemoveProjectAssignmentDTO(1, 2, 45);

        // Mock entity
        User manager = new User();
        manager.setId(2);

        Project relatedProject = new Project();
        relatedProject.setId(1);
        relatedProject.setManager(manager);

        User assignedUser = new User();
        assignedUser.setId(45);

        Set<Task> tasks = new HashSet<>();

        Task task1 = new Task();
        task1.setProject(relatedProject);
        task1.setUser(assignedUser);
        tasks.add(task1);

        Task task2 = new Task();
        task2.setProject(relatedProject);
        task2.setUser(assignedUser);
        tasks.add(task2);

        Project nonRelatedProject = new Project();
        nonRelatedProject.setId(3);

        Task task3 = new Task();
        task3.setProject(nonRelatedProject);
        task3.setUser(assignedUser);
        tasks.add(task3);

        assignedUser.setTasks(tasks);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(relatedProject.getId());
        projectUser.setProject(relatedProject);
        projectUser.setUser(assignedUser);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.of(projectUser));
        Mockito.doNothing().when(this.projectUserRepository).deleteById(Mockito.any(ProjectUserCompositeKey.class));
        Mockito.doNothing().when(this.notificationService).createNotification(Mockito.any(CreateNotificationDTO.class));

        // Call the service
        this.projectService.removeUserFromProject(removeProjectAssignmentDTO);

        // Check if tasks are removed
        Assertions.assertNull(task1.getUser());
        Assertions.assertNull(task2.getUser());
        Assertions.assertEquals(task3.getUser(), assignedUser);
    }

    // endregion

    // region Project Acceptance

    @Test
    public void invalidProjectIdAndUserId_OnUpdateProjectAcceptance_ShouldThrowException() {
        // Mock DTO
        ProjectAcceptanceDTO projectAcceptanceDTO = new ProjectAcceptanceDTO(1, 1, true);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.updateProjectAcceptance(projectAcceptanceDTO));

        // Check if exception message is same
        Assertions.assertEquals("ProjectService.USER_NOT_FOUND_IN_PROJECT", ex.getMessage());
    }

    @Test
    public void validAccept_OnUpdateProjectAcceptance_ShouldUpdateAcceptedAsTrue() throws ProjectServiceException,
            NotificationServiceException {
        // Mock DTO
        ProjectAcceptanceDTO projectAcceptanceDTO = new ProjectAcceptanceDTO(1, 1, true);

        // Mock entity
        User manager = new User();
        manager.setId(2);

        User assignedUser = new User();
        assignedUser.setId(1);

        Project project = new Project();
        project.setManager(manager);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);
        projectUser.setUser(assignedUser);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.of(projectUser));
        Mockito.when(this.projectUserRepository.save(projectUser)).thenReturn(projectUser);
        Mockito.doNothing().when(this.notificationService).createNotification(Mockito.any(CreateNotificationDTO.class));

        // Check if method indeed throws an exception
        this.projectService.updateProjectAcceptance(projectAcceptanceDTO);

        // Check if exception message is same
        Assertions.assertTrue(projectUser.getHasAccepted());
    }

    @Test
    public void validReject_OnUpdateProjectAcceptance_ShouldUpdateAcceptedAsFalse() throws ProjectServiceException,
            NotificationServiceException {
        // Mock DTO
        ProjectAcceptanceDTO projectAcceptanceDTO = new ProjectAcceptanceDTO(1, 1, false);

        // Mock entity
        User manager = new User();
        manager.setId(2);

        Project relatedProject = new Project();
        relatedProject.setManager(manager);

        User assignedUser = new User();
        assignedUser.setId(1);

        Set<Task> tasks = new HashSet<>();

        Task task1 = new Task();
        task1.setProject(relatedProject);
        task1.setUser(assignedUser);
        tasks.add(task1);

        Task task2 = new Task();
        task2.setProject(relatedProject);
        task2.setUser(assignedUser);
        tasks.add(task2);

        relatedProject.setTasks(tasks);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserId(assignedUser.getId());
        projectUser.setProject(relatedProject);
        projectUser.setUser(assignedUser);

        // Mock repository behavior
        Mockito.when(this.projectUserRepository.findById(Mockito.any(ProjectUserCompositeKey.class)))
                .thenReturn(Optional.of(projectUser));
        Mockito.when(this.projectUserRepository.save(projectUser)).thenReturn(projectUser);
        Mockito.doNothing().when(this.projectUserRepository).deleteById(Mockito.any(ProjectUserCompositeKey.class));
        Mockito.doNothing().when(this.notificationService).createNotification(Mockito.any(CreateNotificationDTO.class));

        // Check if method indeed throws an exception
        this.projectService.updateProjectAcceptance(projectAcceptanceDTO);

        // Check if exception message is same
        Assertions.assertFalse(projectUser.getHasAccepted());
        Assertions.assertNull(task1.getUser());
        Assertions.assertNull(task2.getUser());
    }

    // endregion

    // region Get Project

    @Test
    public void invalidProjectIdAndUserId_OnGetProject_ShouldThrowException() {
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerIdOrUserId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.getProject(1, 1));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    // endregion

    // region Create Project

    @Test
    public void invalidUserId_OnCreateProject_ShouldThrowException() {
        // Mock DTO
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setManagerId(1);

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.createProject(createProjectDTO));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.USER_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidProjectDates_OnCreateProject_ShouldThrowException() {
        // Mock DTO
        CreateProjectDTO createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setManagerId(1);
        createProjectDTO.setStartAt(LocalDateTime.of(2021, Month.FEBRUARY, 1, 12, 0));
        createProjectDTO.setEndAt(LocalDateTime.of(2021, Month.JANUARY, 1, 12, 0));

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new User()));

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.createProject(createProjectDTO));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.INVALID_PROJECT_DATES", ex.getMessage());
    }

    // endregion

    // region Update Project

    @Test
    public void invalidProjectIdAndManagerId_OnUpdateProject_ShouldThrowException() {
        // Mock DTO
        UpdateProjectDTO updateProjectDTO = new UpdateProjectDTO();
        updateProjectDTO.setId(1);
        updateProjectDTO.setManagerId(45);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.updateProject(updateProjectDTO));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.NOT_MATCHING_MANAGER", ex.getMessage());
    }

    @Test
    public void invalidProjectDates_OnUpdateProject_ShouldThrowException() {
        // Mock DTO
        UpdateProjectDTO updateProjectDTO = new UpdateProjectDTO();
        updateProjectDTO.setId(1);
        updateProjectDTO.setManagerId(45);
        updateProjectDTO.setStartAt(LocalDateTime.of(2021, Month.FEBRUARY, 1, 12, 0));
        updateProjectDTO.setEndAt(LocalDateTime.of(2021, Month.JANUARY, 1, 12, 0));

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.of(new Project()));

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.updateProject(updateProjectDTO));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.INVALID_PROJECT_DATES", ex.getMessage());
    }

    // endregion

    // region Delete Project

    @Test
    public void invalidProjectIdAndManagerId_OnDeleteProject_ShouldThrowException() {
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.deleteProject(1, 1));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void valid_OnDeleteProject() throws ProjectServiceException {
        // Mock entity
        Project project = new Project();
        project.setId(3);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.of(project));

        // Call the service
        this.projectService.deleteProject(3, 1);
    }

    // endregion
}

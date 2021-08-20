package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.project.AssignProjectDTO;
import com.bernardawj.notey.dto.project.CreateProjectDTO;
import com.bernardawj.notey.dto.project.UpdateProjectDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

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

    @Test
    public void invalidProjectIdOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, "dummy@email.com");

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidEmailOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, "dummy@email.com");

        // Mock entity
        Project project = new Project();
        project.setId(1);

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
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, "dummy@email.com");

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
    public void invalidManagerAssignmentOnAssignUserToProjectShouldThrowException() {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, "dummy@email.com");

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

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(assignProjectDTO));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.USER_IS_MANAGER", ex.getMessage());
    }

    @Test
    public void validOnAssignUserToProject() throws ProjectServiceException, NotificationServiceException {
        // Mock DTO
        AssignProjectDTO assignProjectDTO = new AssignProjectDTO(1, "user@email.com");

        // Mock entity
        User user = new User();
        user.setId(1);

        User manager = new User();
        manager.setId(2);

        ProjectUser projectUser = new ProjectUser(1, 1, false, new ArrayList<>());

        Project project = new Project();
        project.setId(1);
        project.setManager(manager);
        project.setProjectUsers(new ArrayList<>());
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

    @Test
    public void invalidProjectIdAndUserIdOnGetProjectShouldThrowException() {
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerIdOrUserId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.getProject(1, 1));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidUserIdOnCreateProjectShouldThrowException() {
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
    public void invalidProjectDatesOnCreateProjectShouldThrowException() {
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

    @Test
    public void invalidProjectIdAndManagerIdOnUpdateProjectShouldThrowException() {
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
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidProjectDatesOnUpdateProjectShouldThrowException() {
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

    @Test
    public void invalidProjectIdAndManagerIdOnDeleteProjectShouldThrowException() {
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findProjectByProjectIdAndManagerId(Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> this.projectService.deleteProject(1, 1));

        // Check if exception message is the same
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }
}

package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.AssignProjectDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void invalidProjectIdOnAssignUserToProjectShouldThrow() {
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
    public void invalidEmailOnAssignUserToProjectShouldThrow() {
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
    public void invalidManagerAssignmentOnAssignUserToProjectShouldThrow() {
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
    public void validOnAssignUserToProject() throws ProjectServiceException {
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
        Mockito.when(this.projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        // Call service method
        this.projectService.assignUserToProject(assignProjectDTO);

        // Check if any of the assertions fail
        boolean hasProjectUser = project.getProjectUsers().stream().anyMatch(pu ->
                pu.getProjectId().intValue() == project.getId().intValue() && pu.getUserId() == user.getId().intValue());
        Assertions.assertTrue(hasProjectUser);
    }
}

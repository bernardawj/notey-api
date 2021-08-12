package com.bernardawj.notey.service;

import com.bernardawj.notey.entity.Project;
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
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(1, "dummy@email.com"));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidEmailOnAssignUserToProjectShouldThrow() {
        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Project()));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(1, "dummy@email.com"));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.USER_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidManagerAssignmentOnAssignUserToProjectShouldThrow() {
        // Mock fake entities
        User user = new User();
        user.setId(1);

        User manager = new User();
        manager.setId(1);

        Project project = new Project();
        project.setManager(manager);

        // Mock the behavior of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        // Check if the service method indeed throws an exception
        ProjectServiceException ex = Assertions.assertThrows(ProjectServiceException.class,
                () -> projectService.assignUserToProject(1, "dummy@email.com"));

        // Check if the right message is thrown
        Assertions.assertEquals("ProjectService.USER_IS_MANAGER", ex.getMessage());
    }
}

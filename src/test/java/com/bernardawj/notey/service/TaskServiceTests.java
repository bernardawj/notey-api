package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.TaskServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.TaskRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectUserRepository projectUserRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void invalidProjectIdOnCreateTaskShouldThrowException() {
        // Mock DTO
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setProjectId(1);

        // Mock behaviors of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.createTask(createTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.PROJECT_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidTaskNameExistsInProjectOnCreateTaskShouldThrowException() {
        // Mock DTO
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setName("Dummy task");
        createTaskDTO.setProjectId(1);

        // Mock behaviors of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Project()));
        Mockito.when(this.taskRepository.findByProjectIdAndTaskName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(new Task()));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.createTask(createTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_EXISTS", ex.getMessage());
    }
}

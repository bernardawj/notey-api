package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.*;
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

import java.util.ArrayList;
import java.util.List;
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
        // Mock entity
        Project project = new Project();
        project.setId(1);

        // Mock DTO
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setName("Dummy task");
        createTaskDTO.setProjectId(1);

        // Mock behaviors of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.taskRepository.findByProjectIdAndTaskName(Mockito.anyInt(), Mockito.anyString())).thenReturn(Optional.of(new Task()));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.createTask(createTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_EXISTS", ex.getMessage());
    }

    @Test
    public void invalidTaskIdOnAssignUserToTaskShouldThrowException() {
        // Mock DTO
        AssignTaskDTO assignTaskDTO = new AssignTaskDTO();
        assignTaskDTO.setTaskId(1);
        assignTaskDTO.setUserId(1);
        assignTaskDTO.setManagerId(2);
        assignTaskDTO.setAssign(true);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.assignTaskToUser(assignTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidProjectIdAndUserIdOnAssignUserToTaskShouldThrowException() {
        // Mock DTO
        AssignTaskDTO assignTaskDTO = new AssignTaskDTO();
        assignTaskDTO.setTaskId(1);
        assignTaskDTO.setUserId(1);
        assignTaskDTO.setManagerId(2);
        assignTaskDTO.setAssign(true);

        // Mock task
        Task task = new Task();
        task.setProject(new Project());
        task.getProject().setId(1);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.assignTaskToUser(assignTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.USER_NOT_PART_OF_PROJECT", ex.getMessage());
    }

    @Test
    public void invalidManagerIdOnAssignUserToTaskShouldThrowException() {
        // Mock DTO
        AssignTaskDTO assignTaskDTO = new AssignTaskDTO();
        assignTaskDTO.setTaskId(1);
        assignTaskDTO.setUserId(1);
        assignTaskDTO.setManagerId(2);
        assignTaskDTO.setAssign(true);

        // Mock entity
        Task task = new Task();
        task.setProject(new Project());
        task.getProject().setId(1);
        task.getProject().setManager(new User());
        task.getProject().getManager().setId(45);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(new ProjectUser()));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.assignTaskToUser(assignTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.NOT_MATCHING_MANAGER", ex.getMessage());
    }

    @Test
    public void invalidUnAssignOnAssignUserToTaskShouldThrowException() {
        // Mock DTO
        AssignTaskDTO assignTaskDTO = new AssignTaskDTO();
        assignTaskDTO.setTaskId(1);
        assignTaskDTO.setUserId(1);
        assignTaskDTO.setManagerId(45);
        assignTaskDTO.setAssign(false);

        // Mock entity
        Task task = new Task();
        task.setProject(new Project());
        task.getProject().setId(1);
        task.getProject().setManager(new User());
        task.getProject().getManager().setId(45);
        task.setUser(null);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));
        Mockito.when(this.projectUserRepository.findByProjectIdAndUserId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(new ProjectUser()));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.assignTaskToUser(assignTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.INVALID_UNASSIGNMENT", ex.getMessage());
    }

    @Test
    public void invalidTaskIdOnMarkTaskAsCompletedShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.markTaskAsCompleted(new MarkTaskCompletionDTO()));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidTaskIdOnGetTaskShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.getTask(1, 1));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidUserNotInProjectOnGetTaskShouldThrowException() {
        // Mock entity
        ProjectUser projectUser1 = new ProjectUser();
        projectUser1.setUserId(2);

        ProjectUser projectUser2 = new ProjectUser();
        projectUser2.setUserId(3);

        List<ProjectUser> projectUsers = new ArrayList<>();
        projectUsers.add(projectUser1);
        projectUsers.add(projectUser2);

        Project project = new Project();
        project.setProjectUsers(projectUsers);

        Task task = new Task();
        task.setProject(project);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.getTask(1, 1));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.USER_NOT_PART_OF_PROJECT", ex.getMessage());
    }

    @Test
    public void invalidTaskIdOnUpdateTaskShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.updateTask(new UpdateTaskDTO()));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidManagerIdOnUpdateTaskShouldThrowException() {
        // Mock entity
        Project project = new Project();
        project.setManager(new User());
        project.getManager().setId(1);

        Task task = new Task();
        task.setProject(project);

        // Mock DTO
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setTaskId(1);
        updateTaskDTO.setManagerId(2);

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.updateTask(updateTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.NOT_MATCHING_MANAGER", ex.getMessage());
    }

    @Test
    public void invalidTaskIdAndManagerIdOnDeleteTaskShouldThrowException() {
        // Mock DTO
        DeleteTaskDTO deleteTaskDTO = new DeleteTaskDTO(1, 1);

        // Mock the behavior of repository
        Mockito.when(this.taskRepository.findByTaskIdAndManagerId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.deleteTask(deleteTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }
}

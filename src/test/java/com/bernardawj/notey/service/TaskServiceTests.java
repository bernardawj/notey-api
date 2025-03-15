package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.AssignTaskDTO;
import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.dto.task.MarkTaskCompletionDTO;
import com.bernardawj.notey.dto.task.UpdateTaskDTO;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    // region Create Task

    @Test
    public void invalidProjectId_OnCreateTask_ShouldThrowException() {
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
    public void invalidTaskNameExistsInProject_OnCreateTask_ShouldThrowException() {
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
    public void invalidStartOrEndAt_OnCreateTask_ShouldThrowException() {
        // Mock entity
        Project project = new Project();
        project.setId(1);

        // Mock DTO
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setName("Dummy task");
        createTaskDTO.setProjectId(1);
        createTaskDTO.setStartAt(LocalDateTime.now());
        createTaskDTO.setEndAt(LocalDateTime.now().minus(3, ChronoUnit.DAYS));

        // Mock behaviors of repository
        Mockito.when(this.projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(project));
        Mockito.when(this.taskRepository.findByProjectIdAndTaskName(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.createTask(createTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.INVALID_TASK_DATES", ex.getMessage());
    }

    // endregion

    // region Assign User Task

    @Test
    public void invalidTaskId_OnAssignUserToTask_ShouldThrowException() {
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
    public void invalidProjectIdAndUserId_OnAssignUserToTask_ShouldThrowException() {
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
    public void invalidManagerId_OnAssignUserToTask_ShouldThrowException() {
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
    public void invalidUnAssign_OnAssignUserToTask_ShouldThrowException() {
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

    // endregion

    // region Mark Task As Completed

    @Test
    public void invalidTaskId_OnMarkTaskAsCompleted_ShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.markTaskAsCompleted(new MarkTaskCompletionDTO()));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    // endregion

    // region Get Task

    @Test
    public void invalidTaskId_OnGetTask_ShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.getTask(1, 1));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidUserNotInProject_OnGetTask_ShouldThrowException() {
        // Mock entity
        ProjectUser projectUser1 = new ProjectUser();
        projectUser1.setUserId(2);

        ProjectUser projectUser2 = new ProjectUser();
        projectUser2.setUserId(3);

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser1);
        projectUsers.add(projectUser2);

        User manager = new User();
        manager.setId(45);

        Project project = new Project();
        project.setProjectUsers(projectUsers);
        project.setManager(manager);

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

    // endregion

    // region Update Task

    @Test
    public void invalidTaskId_OnUpdateTask_ShouldThrowException() {
        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.updateTask(new UpdateTaskDTO()));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    @Test
    public void invalidManagerId_OnUpdateTask_ShouldThrowException() {
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
    public void invalidStartOrEndAt_OnUpdateTask_ShouldThrowException() {
        // Mock entity
        Project project = new Project();
        project.setManager(new User());
        project.getManager().setId(2);

        Task task = new Task();
        task.setProject(project);

        // Mock DTO
        UpdateTaskDTO updateTaskDTO = new UpdateTaskDTO();
        updateTaskDTO.setTaskId(1);
        updateTaskDTO.setManagerId(2);
        updateTaskDTO.setStartAt(LocalDateTime.now());
        updateTaskDTO.setEndAt(LocalDateTime.now().minus(3, ChronoUnit.DAYS));

        // Mock behaviors of repository
        Mockito.when(this.taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(task));

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.updateTask(updateTaskDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.INVALID_TASK_DATES", ex.getMessage());
    }

    // endregion

    // region Delete Task

    @Test
    public void invalidTaskIdAndManagerIdOnDeleteTaskShouldThrowException() {
        // Mock the behavior of repository
        Mockito.when(this.taskRepository.findByTaskIdAndManagerId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());

        // Check if method indeed throws an exception
        TaskServiceException ex = Assertions.assertThrows(TaskServiceException.class,
                () -> this.taskService.deleteTask(1, 1));

        // Check if exception message thrown is the same
        Assertions.assertEquals("TaskService.TASK_NOT_FOUND", ex.getMessage());
    }

    // endregion
}

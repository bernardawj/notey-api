package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.exception.TaskServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.TaskRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service(value = "taskService")
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;

    private final String TASK_NOT_FOUND = "TaskService.TASK_NOT_FOUND";
    private final String TASK_NOT_UNDER_USER = "TaskService.TASK_NOT_UNDER_USER";
    private final String USER_NOT_FOUND = "TaskService.USER_NOT_FOUND";
    private final String USER_NOT_PART_OF_PROJECT = "TaskService.USER_NOT_PART_OF_PROJECT";
    private final String NOT_MATCHING_MANAGER = "TaskService.NOT_MATCHING_MANAGER";

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository,
                           UserRepository userRepository, ProjectUserRepository projectUserRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException {
        // Check if project exists
        Optional<Project> optProject = this.projectRepository.findById(createTaskDTO.getProjectId());
        Project project = optProject.orElseThrow(() -> new TaskServiceException("TaskService.PROJECT_NOT_FOUND"));

        // Check if task with the same name exists in the project
        Optional<Task> optTask = this.taskRepository.findByProjectIdAndTaskName(project.getId(),
                createTaskDTO.getName());
        if (optTask.isPresent())
            throw new TaskServiceException("TaskService.TASK_EXISTS");

        // Save the task into database
        Task task = new Task(createTaskDTO.getName(), createTaskDTO.getDescription(), createTaskDTO.getType(), false,
                createTaskDTO.getStartAt(), createTaskDTO.getEndAt(), LocalDateTime.now(ZoneOffset.UTC), project);
        this.taskRepository.save(task);

        // Return DTO
        UserDTO managerDTO = new UserDTO(task.getProject().getManager().getId(),
                task.getProject().getManager().getEmail(), null, task.getProject().getManager().getFirstName(),
                task.getProject().getManager().getLastName());
        ProjectDTO projectDTO = new ProjectDTO(task.getProject().getId(), task.getProject().getName(),
                task.getProject().getDescription(), task.getProject().getStartAt(), task.getProject().getEndAt(),
                task.getProject().getAccessedAt(), managerDTO);
        return new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getType(), task.getCompleted(),
                task.getStartAt(), task.getEndAt(), task.getCreatedAt(), projectDTO, null);
    }

    @Override
    public void assignTaskToUser(Integer taskId, Integer userId, Integer managerId, Boolean assign) throws TaskServiceException {
    }

    @Override
    public void markTaskAsCompleted(Integer taskId, Integer userId, Boolean complete) throws TaskServiceException {
    }

    @Override
    public TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException {
        return null;
    }

    @Override
    public TaskDTO updateTask(Integer taskId, Integer managerId) throws TaskServiceException {
        return null;
    }

    @Override
    public TaskDTO deleteTask(Integer taskId, Integer managerId) throws TaskServiceException {
        return null;
    }
}

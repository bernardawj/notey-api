package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.dto.project.ProjectUserDTO;
import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.PaginationDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.SortType;
import com.bernardawj.notey.dto.shared.filter.TaskFilterDTO;
import com.bernardawj.notey.dto.task.*;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.Project;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.enums.NotificationType;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.TaskServiceException;
import com.bernardawj.notey.repository.ProjectRepository;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.TaskRepository;
import com.bernardawj.notey.utility.shared.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "taskService")
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final NotificationService notificationService;

    private final String TASK_NOT_FOUND = "TaskService.TASK_NOT_FOUND";
    private final String USER_NOT_PART_OF_PROJECT = "TaskService.USER_NOT_PART_OF_PROJECT";
    private final String NOT_MATCHING_MANAGER = "TaskService.NOT_MATCHING_MANAGER";
    private final String INVALID_UNASSIGNMENT = "TaskService.INVALID_UNASSIGNMENT";
    private final String INVALID_TASK_DATES = "TaskService.INVALID_TASK_DATES";

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository,
                           ProjectUserRepository projectUserRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
        this.notificationService = notificationService;
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

        // Validate task dates
        if (createTaskDTO.getStartAt().isAfter(createTaskDTO.getEndAt()))
            throw new TaskServiceException(INVALID_TASK_DATES);

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
                managerDTO);
        return new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getType(), task.getCompleted(),
                task.getStartAt(), task.getEndAt(), task.getCreatedAt(), projectDTO, null);
    }

    @Override
    public void assignTaskToUser(AssignTaskDTO assignTaskDTO) throws TaskServiceException,
            NotificationServiceException {
        // Check if task exists within the database
        Optional<Task> optTask = this.taskRepository.findById(assignTaskDTO.getTaskId());
        Task task = optTask.orElseThrow(() -> new TaskServiceException(TASK_NOT_FOUND));

        // Check if user is part of the project
        Optional<ProjectUser> optProjectUser =
                this.projectUserRepository.findByProjectIdAndUserId(task.getProject().getId(),
                        assignTaskDTO.getUserId());
        ProjectUser projectUser = optProjectUser.orElseThrow(() -> new TaskServiceException(USER_NOT_PART_OF_PROJECT));

        // Check if project manager is correct
        if (task.getProject().getManager().getId().intValue() != assignTaskDTO.getManagerId())
            throw new TaskServiceException(NOT_MATCHING_MANAGER);

        // Update user assignment on the task
        if (assignTaskDTO.getAssign()) {
            task.setUser(projectUser.getUser());
        } else {
            // Check if there are any user assignment before un-assigning
            if (task.getUser() == null)
                throw new TaskServiceException(INVALID_UNASSIGNMENT);

            task.setUser(null);
        }

        // Save to database
        this.taskRepository.save(task);

        // Create notification
        User manager = task.getProject().getManager();

        CreateNotificationDTO createNotificationDTO;
        if (assignTaskDTO.getAssign()) {
            createNotificationDTO = CreateNotificationDTO.notificationBuilder(task.getName(),
                    manager.getFullName(), NotificationType.TASK_ALLOCATION, manager.getId(), projectUser.getUserId());
        } else {
            createNotificationDTO = CreateNotificationDTO.notificationBuilder(task.getName(),
                    manager.getFullName(), NotificationType.TASK_ALLOCATION_REMOVAL, manager.getId(),
                    projectUser.getUserId());
        }

        this.notificationService.createNotification(createNotificationDTO);
    }

    @Override
    public void markTaskAsCompleted(MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException,
            NotificationServiceException {
        // Check if task exists within the database
        Optional<Task> optTask = this.taskRepository.findByTaskIdAndUserId(markTaskCompletionDTO.getTaskId(),
                markTaskCompletionDTO.getUserId());
        Task task = optTask.orElseThrow(() -> new TaskServiceException(TASK_NOT_FOUND));

        // Update task completion and save to database
        task.setCompleted(markTaskCompletionDTO.getComplete());
        this.taskRepository.save(task);

        // Create notification
        User user = task.getUser();
        User manager = task.getProject().getManager();

        CreateNotificationDTO createNotificationDTO;
        if (markTaskCompletionDTO.getComplete()) {
            createNotificationDTO = CreateNotificationDTO.notificationBuilder(task.getName(),
                    user.getFullName(), NotificationType.TASK_MARK_COMPLETED, user.getId(), manager.getId());
        } else {
            createNotificationDTO = CreateNotificationDTO.notificationBuilder(task.getName(),
                    user.getFullName(), NotificationType.TASK_MARK_INCOMPLETE, user.getId(), manager.getId());
        }

        this.notificationService.createNotification(createNotificationDTO);
    }

    @Override
    public TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException {
        // Check if task exists within the database
        Optional<Task> optTask = this.taskRepository.findById(taskId);
        Task task = optTask.orElseThrow(() -> new TaskServiceException(TASK_NOT_FOUND));

        // Check if user belongs to the project
        boolean isProjectUser = task.getProject().getProjectUsers().stream().anyMatch(projectUser ->
                projectUser.getUserId().intValue() == userId.intValue()) || task.getProject().getManager().getId().intValue() == userId.intValue();
        if (!isProjectUser)
            throw new TaskServiceException(USER_NOT_PART_OF_PROJECT);

        // Return DTO
        return populateTaskDTO(task);
    }

    @Override
    public TaskListDTO getAllUserTasks(GetUserTasksDTO getUserTasksDTO) {
        // Retrieve all projects based on the manager ID
        Pageable pageable = PageableUtil.populatePageable(getUserTasksDTO.getSort(), getUserTasksDTO.getInputPage());

        TaskFilterDTO taskFilter = getUserTasksDTO.getFilter();
        Page<Task> tasks;
        if (taskFilter.getType() != null && taskFilter.getCompleted() != null) {
            tasks = this.taskRepository.findAllByUserIdAndPaginationAndTypeOrCompleted(getUserTasksDTO.getUserId(),
                    taskFilter.getSearchString(), taskFilter.getType(), taskFilter.getCompleted(), pageable);
        } else if (taskFilter.getType() != null) {
            tasks = this.taskRepository.findAllByUserIdAndPaginationAndType(getUserTasksDTO.getUserId(),
                    taskFilter.getSearchString(), taskFilter.getType(), pageable);
        } else if (taskFilter.getCompleted() != null) {
            tasks = this.taskRepository.findAllByUserIdAndPaginationAndCompleted(getUserTasksDTO.getUserId(),
                    taskFilter.getSearchString(), taskFilter.getCompleted(), pageable);
        } else {
            tasks = this.taskRepository.findAllByUserIdAndPagination(getUserTasksDTO.getUserId(),
                    getUserTasksDTO.getFilter().getSearchString(), pageable);
        }

        // Populate it into DTO
        return populateTaskListDTO(tasks, getUserTasksDTO.getInputPage().getPageNo(), tasks.getTotalPages());
    }

    @Override
    public TaskListDTO getAllProjectTasks(GetProjectTasksDTO getProjectTasksDTO) {
        // Retrieve all projects based on the manager ID
        Pageable pageable = PageableUtil.populatePageable(getProjectTasksDTO.getSort(), getProjectTasksDTO.getInputPage());

        TaskFilterDTO taskFilter = getProjectTasksDTO.getFilter();
        Page<Task> tasks;
        if (taskFilter.getType() != null && taskFilter.getCompleted() != null) {
            tasks = this.taskRepository.findAllByProjectIdAndPaginationAndTypeOrCompleted(getProjectTasksDTO.getProjectId(),
                    taskFilter.getSearchString(), taskFilter.getType(), taskFilter.getCompleted(), pageable);
        } else if (taskFilter.getType() != null) {
            tasks = this.taskRepository.findAllByProjectIdAndPaginationAndType(getProjectTasksDTO.getProjectId(),
                    taskFilter.getSearchString(), taskFilter.getType(), pageable);
        } else if (taskFilter.getCompleted() != null) {
            tasks = this.taskRepository.findAllByProjectIdAndPaginationAndCompleted(getProjectTasksDTO.getProjectId(),
                    taskFilter.getSearchString(), taskFilter.getCompleted(), pageable);
        } else {
            tasks = this.taskRepository.findAllByProjectIdAndPagination(getProjectTasksDTO.getProjectId(),
                    getProjectTasksDTO.getFilter().getSearchString(), pageable);
        }

        // Populate it into DTO'
        return populateTaskListDTO(tasks, getProjectTasksDTO.getInputPage().getPageNo(), tasks.getTotalPages());
    }

    @Override
    public TaskDTO updateTask(UpdateTaskDTO updateTaskDTO) throws TaskServiceException {
        // Check if task exists in the database
        Optional<Task> optTask = this.taskRepository.findById(updateTaskDTO.getTaskId());
        Task task = optTask.orElseThrow(() -> new TaskServiceException(TASK_NOT_FOUND));

        // Check if task is being updated by project manager
        if (task.getProject().getManager().getId().intValue() != updateTaskDTO.getManagerId().intValue())
            throw new TaskServiceException(NOT_MATCHING_MANAGER);

        // Validate task dates
        if (updateTaskDTO.getStartAt().isAfter(updateTaskDTO.getEndAt()))
            throw new TaskServiceException(INVALID_TASK_DATES);

        // Update task and save it into database
        task.setName(updateTaskDTO.getName());
        task.setDescription(updateTaskDTO.getDescription());
        task.setType(updateTaskDTO.getType());
        task.setCompleted(updateTaskDTO.getCompleted());
        task.setStartAt(updateTaskDTO.getStartAt());
        task.setEndAt(updateTaskDTO.getEndAt());
        this.taskRepository.save(task);

        // Return DTO
        return populateTaskDTO(task);
    }

    @Override
    public void deleteTask(Integer taskId, Integer managerId) throws TaskServiceException {
        // Check if tasks exists within the database
        Optional<Task> optTask = this.taskRepository.findByTaskIdAndManagerId(taskId, managerId);
        Task task = optTask.orElseThrow(() -> new TaskServiceException(TASK_NOT_FOUND));

        // Delete from database
        this.taskRepository.deleteById(task.getId());
    }

    private TaskDTO populateTaskDTO(Task task) {
        Project project = task.getProject();

        // Populate manager details
        User manager = project.getManager();
        UserDTO managerDTO = new UserDTO();
        managerDTO.setId(manager.getId());
        managerDTO.setEmail(manager.getEmail());
        managerDTO.setFirstName(manager.getFirstName());
        managerDTO.setLastName(manager.getLastName());

        // Populate project details
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setManager(managerDTO);

        // Populate project users
        List<ProjectUserDTO> projectUsersDTO = new ArrayList<>();
        project.getProjectUsers().forEach(assignedUser -> {
            projectUsersDTO.add(new ProjectUserDTO(assignedUser.getUser().getId(), assignedUser.getUser().getEmail(),
                    assignedUser.getUser().getFirstName(), assignedUser.getUser().getLastName(),
                    assignedUser.getHasAccepted()));
        });
        projectDTO.setAssignedUsers(projectUsersDTO);

        // Populate task
        TaskDTO taskDTO = new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getType(),
                task.getCompleted(),
                task.getStartAt(), task.getEndAt(), task.getCreatedAt(), projectDTO, null);

        // Populate user
        if (task.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(task.getUser().getId());
            userDTO.setEmail(task.getUser().getEmail());
            userDTO.setFirstName(task.getUser().getFirstName());
            userDTO.setLastName(task.getUser().getLastName());
            taskDTO.setUser(userDTO);
        }

        return taskDTO;
    }

    private List<TaskDTO> populateTasksDTO(Iterable<Task> tasks) {
        List<TaskDTO> tasksDTO = new ArrayList<>();
        tasks.forEach(task -> {
            tasksDTO.add(populateTaskDTO(task));
        });
        return tasksDTO;
    }

    private TaskListDTO populateTaskListDTO(Iterable<Task> tasks, Integer pageNo, Integer totalPages) {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setTasks(populateTasksDTO(tasks));
        taskListDTO.setPagination(new PaginationDTO(pageNo, totalPages));
        return taskListDTO;
    }
}

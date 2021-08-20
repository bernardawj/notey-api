package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.GetProjectTasksDTO;
import com.bernardawj.notey.dto.task.*;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.TaskServiceException;

public interface TaskService {

    TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException;

    void assignTaskToUser(AssignTaskDTO assignTaskDTO) throws TaskServiceException, NotificationServiceException;

    void markTaskAsCompleted(MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException,
            NotificationServiceException;

    TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException;

    TaskListDTO getAllUserTasks(GetUserTasksDTO getUserTasksDTO);

    TaskListDTO getAllProjectTasks(GetProjectTasksDTO getProjectTasksDTO);

    TaskDTO updateTask(UpdateTaskDTO updateTaskDTO) throws TaskServiceException;

    void deleteTask(Integer taskId, Integer managerId) throws TaskServiceException;
}

package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.AssignTaskDTO;
import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.dto.task.MarkTaskCompletionDTO;
import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.exception.TaskServiceException;

public interface TaskService {

    TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException;

    void assignTaskToUser(AssignTaskDTO assignTaskDTO) throws TaskServiceException;

    void markTaskAsCompleted(MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException;

    TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException;

    TaskDTO updateTask(Integer taskId, Integer managerId) throws TaskServiceException;

    TaskDTO deleteTask(Integer taskId, Integer managerId) throws TaskServiceException;
}

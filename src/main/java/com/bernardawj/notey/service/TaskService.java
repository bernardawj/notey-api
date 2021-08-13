package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.exception.TaskServiceException;

public interface TaskService {

    TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException;

    void assignTaskToUser(Integer taskId, Integer userId, Integer managerId, Boolean assign) throws TaskServiceException;

    void markTaskAsCompleted(Integer taskId, Integer userId, Boolean complete) throws TaskServiceException;

    TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException;

    TaskDTO updateTask(Integer taskId, Integer managerId) throws TaskServiceException;

    TaskDTO deleteTask(Integer taskId, Integer managerId) throws TaskServiceException;
}

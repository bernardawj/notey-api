package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.*;
import com.bernardawj.notey.exception.TaskServiceException;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException;

    void assignTaskToUser(AssignTaskDTO assignTaskDTO) throws TaskServiceException;

    void markTaskAsCompleted(MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException;

    TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException;

    List<TaskDTO> getAllUserTasks(Integer userId) throws TaskServiceException;

    TaskDTO updateTask(Integer taskId, Integer managerId) throws TaskServiceException;

    TaskDTO deleteTask(Integer taskId, Integer managerId) throws TaskServiceException;
}

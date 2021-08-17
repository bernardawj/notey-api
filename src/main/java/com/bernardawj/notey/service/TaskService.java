package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.task.*;
import com.bernardawj.notey.exception.TaskServiceException;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(CreateTaskDTO createTaskDTO) throws TaskServiceException;

    void assignTaskToUser(AssignTaskDTO assignTaskDTO) throws TaskServiceException;

    void markTaskAsCompleted(MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException;

    TaskDTO getTask(Integer taskId, Integer userId) throws TaskServiceException;

    TaskListDTO getAllUserTasks(Integer userId, Integer pageNo, Integer pageSize) throws TaskServiceException;

    TaskListDTO getAllProjectTasks(Integer projectId, Integer pageNo, Integer pageSize) throws TaskServiceException;

    TaskDTO updateTask(UpdateTaskDTO updateTaskDTO) throws TaskServiceException;

    void deleteTask(DeleteTaskDTO deleteTaskDTO) throws TaskServiceException;
}

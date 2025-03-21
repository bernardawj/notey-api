package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.task.*;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.TaskServiceException;
import com.bernardawj.notey.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/task")
@Validated
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) throws TaskServiceException {
        TaskDTO createdTaskDTO = this.taskService.createTask(createTaskDTO);
        return new ResponseEntity<>(createdTaskDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<Void> assignTaskToUser(@RequestBody @Valid AssignTaskDTO assignTaskDTO) throws TaskServiceException, NotificationServiceException {
        this.taskService.assignTaskToUser(assignTaskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/mark-completion")
    public ResponseEntity<Void> markTaskAsCompleted(@RequestBody @Valid MarkTaskCompletionDTO markTaskCompletionDTO)
            throws TaskServiceException, NotificationServiceException {
        this.taskService.markTaskAsCompleted(markTaskCompletionDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{taskId}/{userId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskId") Integer taskId,
                                           @PathVariable("userId") Integer userId) throws TaskServiceException {
        TaskDTO taskDTO = this.taskService.getTask(taskId, userId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<TaskListDTO> getAllUserTasks(@RequestBody @Valid GetUserTasksDTO getUserTasksDTO) {
        TaskListDTO tasksDTO = this.taskService.getAllUserTasks(getUserTasksDTO);
        return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/project")
    public ResponseEntity<TaskListDTO> getAllProjectTasks(@RequestBody @Valid GetProjectTasksDTO getProjectTasksDTO) {
        TaskListDTO taskListDTO = this.taskService.getAllProjectTasks(getProjectTasksDTO);
        return new ResponseEntity<>(taskListDTO, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<TaskDTO> updateTask(@RequestBody @Valid UpdateTaskDTO updateTaskDTO) throws TaskServiceException {
        TaskDTO taskDTO = this.taskService.updateTask(updateTaskDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{taskId}/{managerId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Integer taskId,
                                           @PathVariable("managerId") Integer managerId) throws TaskServiceException {
        this.taskService.deleteTask(taskId, managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

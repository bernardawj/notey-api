package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.task.AssignTaskDTO;
import com.bernardawj.notey.dto.task.CreateTaskDTO;
import com.bernardawj.notey.dto.task.MarkTaskCompletionDTO;
import com.bernardawj.notey.dto.task.TaskDTO;
import com.bernardawj.notey.exception.TaskServiceException;
import com.bernardawj.notey.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/project")
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskDTO createTaskDTO) throws TaskServiceException {
        TaskDTO createdTaskDTO = this.taskService.createTask(createTaskDTO);
        return new ResponseEntity<>(createdTaskDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<Void> assignTaskToUser(@RequestBody AssignTaskDTO assignTaskDTO) throws TaskServiceException {
        this.taskService.assignTaskToUser(assignTaskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/mark-completion")
    public ResponseEntity<Void> markTaskAsCompleted(@RequestBody MarkTaskCompletionDTO markTaskCompletionDTO) throws TaskServiceException {
        this.taskService.markTaskAsCompleted(markTaskCompletionDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{taskId}/{userId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskId") Integer taskId,
                                           @PathVariable("userId") Integer userId) throws TaskServiceException {
        TaskDTO taskDTO = this.taskService.getTask(taskId, userId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }
}

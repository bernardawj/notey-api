package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.dto.user.ProjectAcceptanceDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(path = "/managed")
    public ResponseEntity<ProjectListDTO> getAllManagedProjects(@RequestBody GetManagedProjectDTO getManagedProjectDTO)
            throws UserServiceException {
        ProjectListDTO projectListDTO = this.projectService.getAllManagedProjects(getManagedProjectDTO);
        return new ResponseEntity<>(projectListDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assigned")
    public ResponseEntity<ProjectListDTO> getAllAssignedProjects(@RequestBody GetAssignedProjectDTO getAssignedProjectDTO)
            throws UserServiceException {
        ProjectListDTO projectListDTO = this.projectService.getAllAssignedProjects(getAssignedProjectDTO);
        return new ResponseEntity<>(projectListDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<Void> assignUserToProject(@RequestBody AssignProjectDTO assignProjectDTO)
            throws ProjectServiceException, NotificationServiceException {
        this.projectService.assignUserToProject(assignProjectDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/remove-assignment")
    public ResponseEntity<Void> removeUserFromProject(@RequestBody RemoveProjectAssignmentDTO removeProjectAssignmentDTO)
            throws ProjectServiceException, NotificationServiceException {
        this.projectService.removeUserFromProject(removeProjectAssignmentDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/acceptance")
    public ResponseEntity<Void> updateProjectAcceptance(@RequestBody ProjectAcceptanceDTO projectAcceptanceDTO) throws ProjectServiceException, NotificationServiceException {
        this.projectService.updateProjectAcceptance(projectAcceptanceDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{projectId}/{userId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable("projectId") Integer projectId,
                                                 @PathVariable("userId") Integer userId)
            throws ProjectServiceException {
        ProjectDTO projectDTO = this.projectService.getProject(projectId, userId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody CreateProjectDTO createProjectDTO) throws UserServiceException,
            ProjectServiceException {
        ProjectDTO returnedProjectDTO = this.projectService.createProject(createProjectDTO);
        return new ResponseEntity<>(returnedProjectDTO, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody UpdateProjectDTO updateProjectDTO) throws ProjectServiceException {
        ProjectDTO updatedProjectDTO = this.projectService.updateProject(updateProjectDTO);
        return new ResponseEntity<>(updatedProjectDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{projectId}/{managerId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") Integer projectId,
                                              @PathVariable("managerId") Integer managerId) throws ProjectServiceException {
        this.projectService.deleteProject(projectId, managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

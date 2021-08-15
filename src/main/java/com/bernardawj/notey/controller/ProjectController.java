package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.project.*;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping(path = "/managed/{managerId}")
    public ResponseEntity<List<ProjectDTO>> getAllManagedProjects(@PathVariable("managerId") Integer managerId)
            throws UserServiceException {
        List<ProjectDTO> projectsDTO = this.projectService.getAllManagedProjects(managerId);
        return new ResponseEntity<>(projectsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/assigned/{userId}")
    public ResponseEntity<List<ProjectDTO>> getAllAssignedProjects(@PathVariable("userId") Integer userId)
            throws UserServiceException {
        List<ProjectDTO> projectsDTO = this.projectService.getAllAssignedProjects(userId);
        return new ResponseEntity<>(projectsDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<Void> assignUserToProject(@RequestBody AssignProjectDTO assignProjectDTO)
            throws UserServiceException, ProjectServiceException {
        this.projectService.assignUserToProject(assignProjectDTO);
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

    @DeleteMapping()
    public ResponseEntity<Void> deleteProject(@RequestBody DeleteProjectDTO deleteProjectDTO) throws ProjectServiceException {
        this.projectService.deleteProject(deleteProjectDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

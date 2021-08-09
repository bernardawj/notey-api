package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.project.AssignProjectDTO;
import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/project")
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

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable("projectId") Integer projectId)
            throws ProjectServiceException {
        ProjectDTO projectDTO = this.projectService.getProject(projectId);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<Void> assignUserToProject(@RequestBody AssignProjectDTO assignProjectDTO)
            throws UserServiceException, ProjectServiceException {
        this.projectService.assignUserToProject(assignProjectDTO.getProjectId(), assignProjectDTO.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) throws UserServiceException,
            ProjectServiceException {
        ProjectDTO returnedProjectDTO = this.projectService.addProject(projectDTO);
        return new ResponseEntity<>(returnedProjectDTO, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) throws ProjectServiceException {
        ProjectDTO updatedProjectDTO = this.projectService.updateProject(projectDTO);
        return new ResponseEntity<>(updatedProjectDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{projectId}/{managerId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") Integer projectId,
                                              @PathVariable("managerId") Integer managerId) throws ProjectServiceException {
        this.projectService.deleteProject(projectId, managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

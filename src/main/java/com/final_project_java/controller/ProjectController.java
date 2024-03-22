package com.final_project_java.controller;


import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Project;
import com.final_project_java.service.ProjectService;
import com.final_project_java.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/projects")
public class ProjectController {
    public final ProjectService projectService;

    @GetMapping //http://localhost:8081/api/projects
    public ResponseEntity<ApiResponse> getAllProjects() {
        List<Project> projectsList = projectService.getAllProjects();
        if (projectsList.isEmpty()) {
            throw new ResourceNotFoundException("No projects found in DB");
        }
        return ResponseEntity.ok(ApiResponse.success("Projects list",projectsList));
    }

    @GetMapping("/projectsById/{id}")
    public ResponseEntity<ApiResponse> getAllProjectsById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.getProjectById(id);
        projectOptional.orElseThrow(() ->
                new ResourceNotFoundException("The project with id: " + id + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("Project by id", projectOptional.get()));
    }


    @PostMapping("/addProject")
    public ResponseEntity<ApiResponse> saveProject(@RequestBody Project project) {
        return ResponseEntity.ok(ApiResponse.success("add project", projectService.saveProject(project)));
    }

    @PutMapping("/updateProject")
    public ResponseEntity<ApiResponse> updateProject(@RequestBody Project project) {
        if(project.getId() == null){
            throw new ResourceNotFoundException("Project id is not valid");
        }
        Optional<Project> orderOptional = projectService.getProjectById(project.getId());
        orderOptional.orElseThrow(()->
                new ResourceNotFoundException("Project with id: " + project.getId() + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("update project", projectService.updateProject(project)));
    }

    @DeleteMapping("/deleteProjectById/{id}")
    public ResponseEntity<ApiResponse> deleteProjectById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.getProjectById(id);
        projectOptional.orElseThrow(() ->
                new ResourceNotFoundException("The project with id: " + id + " doesn't exist in DB"));
        projectService.deleteProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("Project with id: " + id + " was deleted successfully",null));
    }

}

package com.final_project_java.service;



import com.final_project_java.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<Project> getProjectById(Long id);
    List<Project> getAllProjects();
    Project saveProject(Project project);
    Project updateProject(Project project);
    void deleteProjectById(Long id);

}

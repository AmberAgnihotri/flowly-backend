
package com.flowly.flowlybackend.controller;

import com.flowly.flowlybackend.entity.ProjectBoard;
import com.flowly.flowlybackend.repository.ProjectBoardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-boards")
public class ProjectBoardController {

    private final ProjectBoardRepository projectBoardRepository;

    public ProjectBoardController(ProjectBoardRepository projectBoardRepository) {
        this.projectBoardRepository = projectBoardRepository;
    }

    @GetMapping
    public List<ProjectBoard> getProjectBoards(
            @RequestParam Long ownerId) {
        return projectBoardRepository.findByOwnerId(ownerId);
    }
}

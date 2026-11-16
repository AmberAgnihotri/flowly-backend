
package com.flowly.flowlybackend.controller;

import com.flowly.flowlybackend.dto.CreateProjectBoardRequest;
import com.flowly.flowlybackend.dto.ProjectBoardResponse;
import com.flowly.flowlybackend.dto.UpdateProjectBoardRequest;
import com.flowly.flowlybackend.entity.ProjectBoard;
import com.flowly.flowlybackend.repository.ProjectBoardRepository;
import com.flowly.flowlybackend.repository.UserRepository;
import com.flowly.flowlybackend.service.ProjectBoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-boards")
public class ProjectBoardController {

    private final ProjectBoardService projectBoardService;
    private final ProjectBoardRepository projectBoardRepository;
    private final UserRepository userRepository;

    public ProjectBoardController(
            ProjectBoardService projectBoardService,
            ProjectBoardRepository projectBoardRepository,
            UserRepository userRepository) {
        this.projectBoardService = projectBoardService;
        this.projectBoardRepository = projectBoardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<ProjectBoardResponse> getProjectBoards(
            Authentication authentication) {

        Long ownerId = userRepository.findByUsername(authentication.getName())
                .orElseThrow()
                .getId();

        return projectBoardRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectBoardResponse createProjectBoard(
            @Valid @RequestBody CreateProjectBoardRequest request,
            Authentication authentication) {

        return projectBoardService.createProjectBoard(
                authentication.getName(),
                request.name()
        );
    }

    @PutMapping("/{projectBoardId}")
    public ProjectBoardResponse updateProjectBoard(
            @PathVariable Long projectBoardId,
            @Valid @RequestBody UpdateProjectBoardRequest request,
            Authentication authentication) {

        return projectBoardService.updateProjectBoard(
                projectBoardId,
                authentication.getName(),
                request.name()
        );
    }

    @DeleteMapping("/{projectBoardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectBoard(
            @PathVariable Long projectBoardId,
            Authentication authentication) {

        projectBoardService.deleteProjectBoard(
                projectBoardId,
                authentication.getName()
        );
    }

    private ProjectBoardResponse toResponse(ProjectBoard board) {
        return new ProjectBoardResponse(
                board.getId(),
                board.getName(),
                board.getCreatedAt()
        );
    }
}

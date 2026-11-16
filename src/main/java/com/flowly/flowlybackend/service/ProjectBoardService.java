
package com.flowly.flowlybackend.service;

import com.flowly.flowlybackend.dto.ProjectBoardResponse;
import com.flowly.flowlybackend.entity.ProjectBoard;
import com.flowly.flowlybackend.entity.User;
import com.flowly.flowlybackend.repository.ProjectBoardRepository;
import com.flowly.flowlybackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final UserRepository userRepository;

    public ProjectBoardService(
            ProjectBoardRepository projectBoardRepository,
            UserRepository userRepository) {
        this.projectBoardRepository = projectBoardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProjectBoardResponse createProjectBoard(
            String username,
            String name) {

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gebruiker niet gevonden."
                ));

        ProjectBoard projectBoard = new ProjectBoard(
                owner,
                name.trim()
        );

        ProjectBoard savedBoard =
                projectBoardRepository.save(projectBoard);

        return toResponse(savedBoard);
    }

    @Transactional
    public ProjectBoardResponse updateProjectBoard(
            Long projectBoardId,
            String username,
            String name) {

        ProjectBoard projectBoard =
                findOwnedProjectBoard(projectBoardId, username);

        projectBoard.setName(name.trim());

        ProjectBoard updatedBoard =
                projectBoardRepository.save(projectBoard);

        return toResponse(updatedBoard);
    }

    @Transactional
    public void deleteProjectBoard(
            Long projectBoardId,
            String username) {

        ProjectBoard projectBoard =
                findOwnedProjectBoard(projectBoardId, username);

        projectBoardRepository.delete(projectBoard);
    }

    private ProjectBoard findOwnedProjectBoard(
            Long projectBoardId,
            String username) {

        ProjectBoard projectBoard =
                projectBoardRepository.findById(projectBoardId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Projectboard niet gevonden."
                        ));

        if (!projectBoard.getOwner().getUsername().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Projectboard niet gevonden."
            );
        }

        return projectBoard;
    }

    private ProjectBoardResponse toResponse(ProjectBoard projectBoard) {
        return new ProjectBoardResponse(
                projectBoard.getId(),
                projectBoard.getName(),
                projectBoard.getCreatedAt()
        );
    }
}

package com.flowly.flowlybackend.service;

import com.flowly.flowlybackend.dto.CreateTaskRequest;
import com.flowly.flowlybackend.dto.TaskResponse;
import com.flowly.flowlybackend.dto.UpdateTaskRequest;
import com.flowly.flowlybackend.entity.ProjectBoard;
import com.flowly.flowlybackend.entity.Task;
import com.flowly.flowlybackend.repository.ProjectBoardRepository;
import com.flowly.flowlybackend.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectBoardRepository projectBoardRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectBoardRepository projectBoardRepository) {
        this.taskRepository = taskRepository;
        this.projectBoardRepository = projectBoardRepository;
    }

    @Transactional
    public TaskResponse createTask(
            Long projectBoardId,
            String username,
            CreateTaskRequest request) {

        ProjectBoard projectBoard =
                findOwnedProjectBoard(projectBoardId, username);

        Task task = new Task(
                projectBoard,
                request.title().trim(),
                request.description()
        );

        Task savedTask = taskRepository.save(task);

        return toResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(
            Long projectBoardId,
            String username) {

        findOwnedProjectBoard(projectBoardId, username);

        return taskRepository.findByProjectBoardId(projectBoardId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse updateTask(
            Long projectBoardId,
            Long taskId,
            String username,
            UpdateTaskRequest request) {

        findOwnedProjectBoard(projectBoardId, username);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Taak niet gevonden."
                ));

        if (!task.getProjectBoard().getId().equals(projectBoardId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Taak niet gevonden."
            );
        }

        task.setTitle(request.title().trim());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task updatedTask = taskRepository.save(task);

        return toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(
            Long projectBoardId,
            Long taskId,
            String username) {

        findOwnedProjectBoard(projectBoardId, username);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Taak niet gevonden."
                ));

        if (!task.getProjectBoard().getId().equals(projectBoardId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Taak niet gevonden."
            );
        }

        taskRepository.delete(task);
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

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getProjectBoard().getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
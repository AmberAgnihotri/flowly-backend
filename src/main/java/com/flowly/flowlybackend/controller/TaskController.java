package com.flowly.flowlybackend.controller;

import com.flowly.flowlybackend.dto.CreateTaskRequest;
import com.flowly.flowlybackend.dto.TaskResponse;
import com.flowly.flowlybackend.dto.UpdateTaskRequest;
import com.flowly.flowlybackend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-boards/{projectBoardId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            @PathVariable Long projectBoardId,
            Authentication authentication) {

        return taskService.getTasks(
                projectBoardId,
                authentication.getName()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @PathVariable Long projectBoardId,
            @Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {

        return taskService.createTask(
                projectBoardId,
                authentication.getName(),
                request
        );
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(
            @PathVariable Long projectBoardId,
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskRequest request,
            Authentication authentication) {

        return taskService.updateTask(
                projectBoardId,
                taskId,
                authentication.getName(),
                request
        );
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @PathVariable Long projectBoardId,
            @PathVariable Long taskId,
            Authentication authentication) {

        taskService.deleteTask(
                projectBoardId,
                taskId,
                authentication.getName()
        );
    }
}
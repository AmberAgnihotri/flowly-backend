package com.flowly.flowlybackend.dto;

import com.flowly.flowlybackend.entity.TaskStatus;

public record TaskResponse(
        Long id,
        Long projectBoardId,
        String title,
        String description,
        TaskStatus status
) {
}
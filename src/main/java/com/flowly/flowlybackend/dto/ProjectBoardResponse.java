package com.flowly.flowlybackend.dto;

import java.time.LocalDateTime;

public record ProjectBoardResponse(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
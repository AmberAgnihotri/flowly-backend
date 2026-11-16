package com.flowly.flowlybackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectBoardRequest(
        @NotBlank(message = "Projectnaam mag niet leeg zijn.")
        @Size(max = 100, message = "Projectnaam mag maximaal 100 tekens bevatten.")
        String name
) {
}
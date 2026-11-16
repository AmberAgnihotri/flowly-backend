package com.flowly.flowlybackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(

        @NotBlank(message = "Taaknaam mag niet leeg zijn.")
        @Size(max = 100, message = "Taaknaam mag maximaal 100 tekens bevatten.")
        String title,

        @Size(max = 300, message = "Beschrijving mag maximaal 300 tekens bevatten.")
        String description

) {
}
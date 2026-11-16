
package com.flowly.flowlybackend.dto;

import com.flowly.flowlybackend.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(

        @NotBlank(message = "Taaknaam mag niet leeg zijn.")
        @Size(max = 100, message = "Taaknaam mag maximaal 100 tekens bevatten.")
        String title,

        @Size(max = 300, message = "Beschrijving mag maximaal 300 tekens bevatten.")
        String description,

        @NotNull(message = "Taakstatus mag niet leeg zijn.")
        TaskStatus status

) {
}

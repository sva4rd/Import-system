package com.project.old_system.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClientNoteRequest {
    @NotBlank
    private String clientGuid;
    @NotBlank
    private String agency;
    @NotBlank
    private LocalDate dateFrom;
    @NotBlank
    private LocalDate dateTo;
}

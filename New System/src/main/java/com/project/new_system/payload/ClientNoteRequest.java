package com.project.new_system.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

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

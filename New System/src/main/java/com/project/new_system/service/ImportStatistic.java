package com.project.new_system.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportStatistic {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime importDateTimeStart = LocalDateTime.now();
    private int updatedCount = 0;
    private int createdCount = 0;

    public void incrementUpdated() {
        this.updatedCount++;
    }

    public void incrementCreated() {
        this.createdCount++;
    }
}

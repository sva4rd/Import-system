package com.project.new_system.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_note")
@Data
@NoArgsConstructor
public class PatientNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time", nullable = false)
    private LocalDateTime lastModifiedDateTime;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private CompanyUser createdByUser;

    @ManyToOne
    @JoinColumn(name = "last_modified_by_user_id")
    private CompanyUser lastModifiedByUser;

    @Column(length = 4000)
    private String note;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @Column(name = "old_guid")
    private String oldGuid;
}

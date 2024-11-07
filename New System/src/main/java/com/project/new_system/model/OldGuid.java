package com.project.new_system.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "old_guid")
public class OldGuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_client_guid", nullable = false)
    private String oldClientGuid;

    @ManyToOne
    @JoinColumn(name = "patient_profile_id", nullable = false)
    private PatientProfile patientProfile;
}


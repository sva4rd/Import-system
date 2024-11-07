package com.project.new_system.repository;

import com.project.new_system.model.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Integer> {
    Optional<PatientNote> findByIdAndPatientId(int id, int patientId);
    Optional<PatientNote> findByOldGuid(String guid);
    List<PatientNote> findByPatientId(int patientId);
}


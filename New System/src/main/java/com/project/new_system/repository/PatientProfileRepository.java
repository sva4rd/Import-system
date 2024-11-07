package com.project.new_system.repository;

import com.project.new_system.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Integer> {
    List<PatientProfile> findByStatusIdIn(List<Integer> statusIds);
}

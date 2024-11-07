package com.project.new_system.repository;

import com.project.new_system.model.OldGuid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OldGuidRepository extends JpaRepository<OldGuid, Long> {
    List<OldGuid> findByPatientProfileId(int patientProfileId);
    List<OldGuid> findByOldClientGuid(String oldClientGuid);
}

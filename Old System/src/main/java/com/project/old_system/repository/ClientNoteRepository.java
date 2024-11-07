package com.project.old_system.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.old_system.model.ClientNote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientNoteRepository extends JpaRepository<ClientNote, String> {
    List<ClientNote> findByClientGuid(String clientGuid);

    List<ClientNote> findByClientGuidAndDatetimeBetween(String clientGuid,
                                                        LocalDateTime startDate,
                                                        LocalDateTime endDate);
}

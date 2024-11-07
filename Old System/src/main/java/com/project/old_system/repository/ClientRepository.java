package com.project.old_system.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.old_system.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findByStatus(String status);
}


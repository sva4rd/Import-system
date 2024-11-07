package com.project.new_system.repository;

import com.project.new_system.model.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Integer> {
    Optional<CompanyUser> findByLogin(String login);
}

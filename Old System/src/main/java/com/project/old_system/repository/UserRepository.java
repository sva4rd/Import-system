package com.project.old_system.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.old_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}


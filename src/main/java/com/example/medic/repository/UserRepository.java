package com.example.medic.repository;

import com.example.medic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(
            String email
    );

    boolean existsByEmail(
            String email
    );

    Page<User>
    findByUsernameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}
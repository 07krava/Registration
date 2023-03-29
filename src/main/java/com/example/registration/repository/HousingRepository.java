package com.example.registration.repository;

import com.example.registration.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HousingRepository extends JpaRepository<Housing, Long> {
    Optional<Housing> findById(Long id);
}

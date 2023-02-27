package com.example.registration.repository;

import com.example.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//TODO remove this repository and where it was using
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}

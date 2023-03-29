package com.example.registration.repository;

import com.example.registration.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByHousingId(Long housingId);
}

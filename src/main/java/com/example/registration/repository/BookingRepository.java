package com.example.registration.repository;

import com.example.registration.model.Booking;
import com.example.registration.model.Housing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHousingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Housing housing, Date startDate, Date endDate);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    List<Booking> findByUserId(Long userId);
}


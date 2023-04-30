package com.example.registration.service.impl;

import com.example.registration.model.Booking;
import com.example.registration.model.Housing;
import com.example.registration.model.User;
import com.example.registration.repository.BookingRepository;
import com.example.registration.repository.HousingRepository;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final HousingRepository housingRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(HousingRepository housingRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.housingRepository = housingRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public List<Housing> findAvailableHousingByDates(Date startDate, Date endDate) {
        List<Housing> availableHousing = new ArrayList<>();
        List<Housing> allHousing = housingRepository.findAll();
        for (Housing housing : allHousing) {
            boolean isHousingAvailable = isHousingAvailableByDates(housing, startDate, endDate);
            if (isHousingAvailable) {
                availableHousing.add(housing);
            }
        }
        return availableHousing;
    }

    public boolean isHousingAvailableByDates(Housing housing, Date startDate, Date endDate) {
        List<Booking> bookings = bookingRepository.findByHousingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(housing, startDate, endDate);
        return bookings.isEmpty();
    }

    public Booking createBooking(Booking booking) {
        User user = userRepository.findById(booking.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + booking.getUser().getId()));
        Housing housing = housingRepository.findById(booking.getHousing().getId()).orElseThrow(() -> new EntityNotFoundException("Housing not found with id " + booking.getHousing().getId()));
        boolean isHousingAvailable = isHousingAvailableByDates(booking.getHousing(), booking.getStartDate(), booking.getEndDate());
        if (!isHousingAvailable) {
            throw new RuntimeException("The housing is not available for the selected dates");
        }
        booking.setUser(user);
        booking.setHousing(housing);
        bookingRepository.save(booking);
        return booking;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        List<Booking> listBooking = user.getBookings();
        return listBooking;
    }
}
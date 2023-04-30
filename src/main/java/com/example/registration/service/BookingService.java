package com.example.registration.service;

import com.example.registration.model.Booking;
import com.example.registration.model.Housing;

import java.util.Date;
import java.util.List;

public interface BookingService {

    List<Housing> findAvailableHousingByDates(Date startDate, Date endDate);

    boolean isHousingAvailableByDates(Housing housing, Date startDate, Date endDate);

    Booking createBooking(Booking booking);

    List<Booking> getBookingsByUserId(Long userId);

}

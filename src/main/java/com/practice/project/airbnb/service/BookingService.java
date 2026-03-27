package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.BookingDto;
import com.practice.project.airbnb.dto.BookingRequest;
import com.practice.project.airbnb.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}

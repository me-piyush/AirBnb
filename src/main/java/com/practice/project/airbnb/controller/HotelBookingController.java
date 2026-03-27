package com.practice.project.airbnb.controller;

import com.practice.project.airbnb.dto.BookingDto;
import com.practice.project.airbnb.dto.BookingRequest;
import com.practice.project.airbnb.dto.GuestDto;
import com.practice.project.airbnb.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;


    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){

        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){

        return ResponseEntity.ok(bookingService.addGuests(bookingId,guestDtoList));
    }


}

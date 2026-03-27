package com.practice.project.airbnb.dto;

import com.practice.project.airbnb.entity.Hotel;
import com.practice.project.airbnb.entity.Room;
import com.practice.project.airbnb.entity.User;
import com.practice.project.airbnb.entity.enums.BookingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
//    private Hotel hotel;
//    private Room room;
//    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.HotelDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long hotelId,HotelDto hotelDto);

    void deleteHotelById(Long id);
}

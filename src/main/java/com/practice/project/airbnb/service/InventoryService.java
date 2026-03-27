package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.HotelDto;
import com.practice.project.airbnb.dto.HotelPriceDto;
import com.practice.project.airbnb.dto.HotelSearchRequestDto;
import com.practice.project.airbnb.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequestDto hotelSearchRequestDto);
}

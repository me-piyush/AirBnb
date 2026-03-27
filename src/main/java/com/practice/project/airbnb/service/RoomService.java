package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRomesInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);

}

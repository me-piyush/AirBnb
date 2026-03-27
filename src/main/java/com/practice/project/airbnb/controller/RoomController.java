package com.practice.project.airbnb.controller;

import com.practice.project.airbnb.dto.RoomDto;
import com.practice.project.airbnb.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        RoomDto room=roomService.createNewRoom(hotelId,roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<RoomDto>> getAllRoomsinHotel(@PathVariable Long hotelId){
        List<RoomDto> rooms= roomService.getAllRomesInHotel(hotelId);
        return  ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById( @PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping({"/{roomId}"})
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }





}

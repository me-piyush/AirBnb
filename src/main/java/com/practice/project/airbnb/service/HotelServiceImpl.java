package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.HotelDto;
import com.practice.project.airbnb.dto.HotelInfoDto;
import com.practice.project.airbnb.dto.RoomDto;
import com.practice.project.airbnb.entity.Hotel;
import com.practice.project.airbnb.entity.Room;
import com.practice.project.airbnb.exception.ResourceNotFoundException;
import com.practice.project.airbnb.repository.HotelRepository;
import com.practice.project.airbnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private  final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto){
        log.info("Creating a hotel with name : {}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with id :{}",hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id){
        log.info("Getting the hotel by id:{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long hotelId, HotelDto hotelDto) {
        log.info("Updating the hotel by id:{}",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(hotelId);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);

    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {

        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+id));

        for (Room room: hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());

        }
        hotelRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId){
        log.info("Activating the hotel by id:{}",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));
        hotel.setActive(true);
        //TODO: Kuch to dikkat aa skti hai

        //assuming only do it once
        for (Room room: hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }



    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();
        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }

}

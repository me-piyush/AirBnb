package com.practice.project.airbnb.controller;

import com.practice.project.airbnb.dto.HotelDto;
import com.practice.project.airbnb.dto.HotelInfoDto;
import com.practice.project.airbnb.dto.HotelPriceDto;
import com.practice.project.airbnb.dto.HotelSearchRequestDto;
import com.practice.project.airbnb.service.HotelService;
import com.practice.project.airbnb.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequestDto hotelSearchRequestDto){
        var page= inventoryService.searchHotels(hotelSearchRequestDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){

        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}

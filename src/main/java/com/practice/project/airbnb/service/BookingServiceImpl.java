package com.practice.project.airbnb.service;

import com.practice.project.airbnb.dto.BookingDto;
import com.practice.project.airbnb.dto.BookingRequest;
import com.practice.project.airbnb.dto.GuestDto;
import com.practice.project.airbnb.entity.*;
import com.practice.project.airbnb.entity.enums.BookingStatus;
import com.practice.project.airbnb.exception.ResourceNotFoundException;
import com.practice.project.airbnb.exception.UnAuthorisedException;
import com.practice.project.airbnb.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final GuestRepository guestRepository;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final CheckoutService checkoutService;

    @Value("${frontend.url}")
    private String frontendUrl;


    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {

        log.info("Initialising  booking for hotel : {}, room {},date {}{}",bookingRequest.getHotelId(),bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(()->
                new ResourceNotFoundException("Hotel not found with id:"+bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(()->
                new ResourceNotFoundException("Room not found with id:"+bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount()
        );

        Long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size()!=daysCount){
            throw  new IllegalStateException("No rooms available for desired search");
        }

        //Reserve room if inventory present

        for (Inventory inventory:inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }
        inventoryRepository.saveAll(inventoryList);



        //TODO: calculate dynamic amount

        //Create the  Booking

        Booking booking = Booking.builder().bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return  modelMapper.map(booking, BookingDto.class);

    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId,List<GuestDto> guestDtoList) {

        log.info("Adding guests for booking with bookingId: {}",bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not found with id:"+bookingId));

        User user= getCurrentUser();

        if (!user.equals(booking.getUser())){
            throw new UnAuthorisedException("Booking does not belong to user with id: "+user.getId());
        }

        if(hasBookingExpired(booking)){
            throw  new IllegalStateException("Booking has expired");
        }
        if(booking.getBookingStatus()!= BookingStatus.RESERVED){
            throw  new IllegalStateException("Booking is not under reserved state, can not add guests");
        }

        for(GuestDto guestDto: guestDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(user);
            guest = guestRepository.save(guest);
        }

        //updating Booking status
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking =bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);

    }

    @Override
    public String initiatePayment(Long bookingId) {
        Booking booking= bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not found with id:"+bookingId
                ));
        User user = getCurrentUser();


        if (!user.equals(booking.getUser())){
            throw new UnAuthorisedException("Booking does not belong to user with id: "+user.getId());
        }

        if(hasBookingExpired(booking)){
            throw  new IllegalStateException("Booking has expired");
        }

        String sessionUrl=checkoutService.getCheckoutSession(booking,
                frontendUrl+"payments/success",frontendUrl+"payments/failure");

        booking.setBookingStatus(BookingStatus.PAYMENT_PENDING);

        return sessionUrl;
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        //Create User

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}

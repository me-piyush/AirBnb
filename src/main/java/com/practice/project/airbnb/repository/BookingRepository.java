package com.practice.project.airbnb.repository;

import com.practice.project.airbnb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}

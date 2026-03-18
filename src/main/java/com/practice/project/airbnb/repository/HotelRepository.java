package com.practice.project.airbnb.repository;

import com.practice.project.airbnb.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository <Hotel, Long>{
}

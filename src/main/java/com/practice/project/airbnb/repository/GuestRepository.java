package com.practice.project.airbnb.repository;

import com.practice.project.airbnb.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
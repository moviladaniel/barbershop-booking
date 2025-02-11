package com.example.barbershop_booking.repositories;

import com.example.barbershop_booking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStatusContainingIgnoreCase(String status);

    List<Booking> findByBarberId(Long barberId);
}

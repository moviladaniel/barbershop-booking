package com.example.barbershop_booking.repositories;

import com.example.barbershop_booking.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
    List<Barber> findByNameContainingIgnoreCase(String name);
    List<Barber> findBySpecialtyContainingIgnoreCase(String specialty);
    List<Barber> findByRatingGreaterThanEqual(Double rating);
}

package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Barber;
import com.example.barbershop_booking.repositories.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberService {

    @Autowired
    private BarberRepository barberRepository;

    public Barber createBarber(Barber barber) {
        return barberRepository.save(barber);
    }

    public List<Barber> getAllBarbers() {
        return barberRepository.findAll();
    }

    public Barber getBarberById(Long id) {
        return barberRepository.findById(id).orElse(null);
    }

    public List<Barber> getBarberByName(String name) {
        return barberRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Barber> getBarberBySpeciality(String speciality) {
        return barberRepository.findBySpecialtyContainingIgnoreCase(speciality);
    }
    public List<Barber> getBarberByRating(Double rating) {
        return barberRepository.findByRatingGreaterThanEqual(rating);
    }

    public Barber updateBarber(Long id, Barber updatedBarber) {
        Barber existingBarber = barberRepository.findById(id).orElse(null);
        if (existingBarber == null) {
            return null;
        }
        existingBarber.setName(updatedBarber.getName());
        existingBarber.setSpecialty(updatedBarber.getSpecialty());
        existingBarber.setRating(updatedBarber.getRating());
        return barberRepository.save(existingBarber);
    }

    public boolean deleteBarber(Long id) {
        if (!barberRepository.existsById(id)) {
            return false;
        }
        barberRepository.deleteById(id);
        return true;
    }
}

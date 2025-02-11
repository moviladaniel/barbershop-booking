package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Barber;
import com.example.barbershop_booking.services.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    @Autowired
    private BarberService barberService;

    @PostMapping
    public ResponseEntity<Barber> createBarber(@RequestBody Barber barber) {
        System.out.println("Creating barber");
        Barber saved = barberService.createBarber(barber);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Barber>> getAllBarbers() {
        System.out.println("Getting all barbers");
        List<Barber> barbers = barberService.getAllBarbers();
        return ResponseEntity.ok(barbers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barber> getBarberById(@PathVariable Long id) {
        Barber barber = barberService.getBarberById(id);
        return (barber != null) ? ResponseEntity.ok(barber) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<Barber>> getBarberByName(@PathVariable String name) {
        List<Barber> barbers = barberService.getBarberByName(name);
        return ResponseEntity.ok(barbers);
    }

    @GetMapping("/bySpeciality/{speciality}")
    public ResponseEntity<List<Barber>> getBarberbySpeciality(@PathVariable String speciality) {
        List<Barber> barbers = barberService.getBarberBySpeciality(speciality);
        return ResponseEntity.ok(barbers);
    }

    @GetMapping("/byRating/{rating}")
    public ResponseEntity<List<Barber>> getBarberbySpeciality(@PathVariable Double rating) {
        List<Barber> barbers = barberService.getBarberByRating(rating);
        return ResponseEntity.ok(barbers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barber> updateBarber(@PathVariable Long id, @RequestBody Barber updatedBarber) {
        Barber updated = barberService.updateBarber(id, updatedBarber);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarber(@PathVariable Long id) {
        boolean deleted = barberService.deleteBarber(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

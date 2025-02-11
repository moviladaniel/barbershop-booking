package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long userId,
            @RequestParam Long barberId,
            @RequestParam List<Long> serviceIds,
            @RequestParam String dateTime
    ) {
        // convert String to LocalDateTime
        LocalDateTime dt = LocalDateTime.parse(dateTime);
        Booking booking = bookingService.createBooking(userId, barberId, serviceIds, dt);
        if (booking == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> all = bookingService.getAllBookings();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return (booking != null) ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }
    @GetMapping("/byStatus")
    public List<Booking> getBookingsByStatus(@RequestParam String status) {
        return bookingService.getBookingsByStatus(status);
    }


    @GetMapping("/byBarber")
    public List<Booking> getBookingsByBarber(@RequestParam Long barberId) {
        return bookingService.getBookingsByBarber(barberId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestParam String newDateTime
    ) {
        LocalDateTime dt = LocalDateTime.parse(newDateTime);
        Booking updated = bookingService.updateBooking(id, dt);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        boolean canceled = bookingService.cancelBooking(id);
        return canceled ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

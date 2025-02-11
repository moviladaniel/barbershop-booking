package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.*;
import com.example.barbershop_booking.repositories.BarberRepository;
import com.example.barbershop_booking.repositories.BookingRepository;
import com.example.barbershop_booking.repositories.ServiceEntityRepository;
import com.example.barbershop_booking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ServiceEntityRepository serviceEntityRepository;

    /**
     * Create a new booking with a given user, barber, services, and datetime.
     */
    public Booking createBooking(Long userId, Long barberId, List<Long> serviceIds, LocalDateTime dateTime) {
        User user = userRepository.findById(userId).orElse(null);
        Barber barber = barberRepository.findById(barberId).orElse(null);
        if (user == null || barber == null) {
            // handle error (null) or throw exception
            return null;
        }

        // retrieve ServiceEntity objects
        List<ServiceEntity> services = serviceEntityRepository.findAllById(serviceIds);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBarber(barber);
        booking.setServices(services);
        booking.setDateTime(dateTime);
        booking.setStatus("SCHEDULED");
        booking.setPaid(false);

        return bookingRepository.save(booking);
    }


    /**
     * Get all bookings.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Get booking by ID.
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatusContainingIgnoreCase(status);
    }

    public List<Booking> getBookingsByBarber(Long barberId) {
        return bookingRepository.findByBarberId(barberId);
    }

    /**
     * Update the dateTime or other details of the booking.
     */
    public Booking updateBooking(Long bookingId, LocalDateTime newDateTime) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return null;
        }
        booking.setDateTime(newDateTime);
        // add any additional changes
        return bookingRepository.save(booking);
    }

    /**
     * Cancel a booking (set status to "CANCELED", or remove it entirely).
     */
    public boolean cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return false;
        }
        booking.setStatus("CANCELED");
        bookingRepository.save(booking);
        return true;
    }

    /**
     * Delete a booking from DB entirely (optional).
     */
    public boolean deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            return false;
        }
        bookingRepository.deleteById(bookingId);
        return true;
    }
}

package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.entities.Notification;
import com.example.barbershop_booking.repositories.BookingRepository;
import com.example.barbershop_booking.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public List<Notification> getNotificationsByDelivered(boolean delivered) {
        return notificationRepository.findByDelivered(delivered);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        Notification existing = notificationRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setMessage(updatedNotification.getMessage());
        existing.setRecipientEmail(updatedNotification.getRecipientEmail());
        existing.setSentTime(updatedNotification.getSentTime());
        existing.setDelivered(updatedNotification.isDelivered());
        // If you need to update the associated booking, handle that as well
        return notificationRepository.save(existing);
    }

    public boolean deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            return false;
        }
        notificationRepository.deleteById(id);
        return true;
    }

    /**
     * Send a reminder notification for a given booking.
     * In reality, you'd integrate with an email/SMS service.
     */
    public Notification sendReminder(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return null; // or throw exception
        }
        Notification notif = new Notification();
        notif.setMessage("Reminder: You have a booking scheduled on " + booking.getDateTime());
        notif.setRecipientEmail(booking.getUser().getEmail());
        notif.setSentTime(LocalDateTime.now());
        notif.setDelivered(true); // or false until you confirm actual delivery
        notif.setBooking(booking);
        return notificationRepository.save(notif);
    }
}

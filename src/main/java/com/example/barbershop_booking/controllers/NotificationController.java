package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Notification;
import com.example.barbershop_booking.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/createNotification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification saved = notificationService.createNotification(notification);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> all = notificationService.getAllNotifications();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return (notification != null) ? ResponseEntity.ok(notification) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byDelivered")
    public List<Notification> getNotificationsByDelivered(@RequestParam boolean delivered) {
        return notificationService.getNotificationsByDelivered(delivered);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification updated) {
        Notification result = notificationService.updateNotification(id, updated);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        boolean deleted = notificationService.deleteNotification(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/sendReminder/{bookingId}")
    public ResponseEntity<Notification> sendReminder(@PathVariable Long bookingId) {
        Notification notif = notificationService.sendReminder(bookingId);
        if (notif == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(notif);
    }
}

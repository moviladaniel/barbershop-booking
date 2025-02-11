package com.example.barbershop_booking.repositories;

import com.example.barbershop_booking.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByDelivered(boolean delivered);
}

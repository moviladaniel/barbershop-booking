package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.entities.Notification;
import com.example.barbershop_booking.repositories.BookingRepository;
import com.example.barbershop_booking.repositories.NotificationRepository;
import com.example.barbershop_booking.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        Notification input = new Notification(null, "Test Msg", "test@demo.com",
                LocalDateTime.now(), false, null);
        Notification saved = new Notification(1L, "Test Msg", "test@demo.com",
                LocalDateTime.now(), false, null);

        when(notificationRepository.save(input)).thenReturn(saved);

        Notification result = notificationService.createNotification(input);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMessage()).isEqualTo("Test Msg");
        verify(notificationRepository, times(1)).save(input);
    }

    @Test
    void testSendReminder_BookingFound() {
        Booking booking = new Booking();
        booking.setId(10L);
        booking.setDateTime(LocalDateTime.of(2025, 3, 10, 10, 0));

        // Create a User object and set it to the booking
        User user = new User();
        user.setEmail("john@example.com");
        booking.setUser(user);
        booking.setUser(user);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        // Mock saving the notification
        Notification saved = new Notification(5L,
                "Reminder: You have a booking scheduled on 2025-03-10T10:00",
                "john@example.com", LocalDateTime.now(), true, booking);
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        Notification notification = notificationService.sendReminder(10L);
        assertThat(notification).isNotNull();
        assertThat(notification.getId()).isEqualTo(5L);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendReminder_BookingNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        Notification notification = notificationService.sendReminder(999L);
        assertThat(notification).isNull();
    }

    @Test
    void testGetNotificationsByDelivered() {
        Notification n1 = new Notification();
        n1.setId(1L);
        n1.setDelivered(true);

        when(notificationRepository.findByDelivered(true))
                .thenReturn(List.of(n1));

        List<Notification> result = notificationService.getNotificationsByDelivered(true);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);

        verify(notificationRepository).findByDelivered(true);
    }
}

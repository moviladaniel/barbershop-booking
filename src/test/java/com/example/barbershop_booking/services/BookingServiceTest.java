package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.*;
import com.example.barbershop_booking.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BarberRepository barberRepository;
    @Mock
    private ServiceEntityRepository serviceEntityRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        // Prepare mock data
        User mockUser = new User(1L, "john@example.com", "pw", "John", "Doe", null);
        Barber mockBarber = new Barber(1L, "Mike", "Fade", 4.5, null);
        ServiceEntity s1 = new ServiceEntity(1L, "Haircut", 30, 15.0);
        ServiceEntity s2 = new ServiceEntity(2L, "Shave", 15, 8.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(barberRepository.findById(1L)).thenReturn(Optional.of(mockBarber));
        when(serviceEntityRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(s1, s2));

        Booking bookingInput = new Booking();
        bookingInput.setUser(mockUser);
        bookingInput.setBarber(mockBarber);
        bookingInput.setServices(List.of(s1, s2));
        bookingInput.setDateTime(LocalDateTime.of(2025, 2, 10, 10, 0));
        bookingInput.setStatus("SCHEDULED");
        bookingInput.setPaid(false);

        Booking savedBooking = new Booking(10L,
                bookingInput.getDateTime(),
                "SCHEDULED",
                false,
                mockUser,
                mockBarber,
                List.of(s1, s2));

        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        // Call the service
        Booking result = bookingService.createBooking(1L, 1L, List.of(1L, 2L),
                LocalDateTime.of(2025, 2, 10, 10, 0));

        // Verify
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getUser().getEmail()).isEqualTo("john@example.com");
        assertThat(result.getBarber().getName()).isEqualTo("Mike");
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_UserOrBarberNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        Booking result = bookingService.createBooking(999L, 1L, List.of(), LocalDateTime.now());
        assertThat(result).isNull();
    }

    @Test
    void testCancelBooking() {
        Booking booking = new Booking();
        booking.setId(5L);
        booking.setStatus("SCHEDULED");

        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));

        boolean canceled = bookingService.cancelBooking(5L);
        assertThat(canceled).isTrue();
        assertThat(booking.getStatus()).isEqualTo("CANCELED");

        verify(bookingRepository).save(booking);
    }

    @Test
    void testGetBookingsByStatus() {
        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setStatus("SCHEDULED");

        Booking b2 = new Booking();
        b2.setId(2L);
        b2.setStatus("scheduled for next week");

        when(bookingRepository.findByStatusContainingIgnoreCase("schedu"))
                .thenReturn(Arrays.asList(b1, b2));

        List<Booking> result = bookingService.getBookingsByStatus("schedu");

        assertThat(result).hasSize(2);
        verify(bookingRepository, times(1))
                .findByStatusContainingIgnoreCase("schedu");
    }


    @Test
    void testGetBookingsByBarber() {
        Booking b1 = new Booking();
        b1.setId(5L);

        when(bookingRepository.findByBarberId(2L))
                .thenReturn(List.of(b1));

        List<Booking> result = bookingService.getBookingsByBarber(2L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(5L);
    }
}

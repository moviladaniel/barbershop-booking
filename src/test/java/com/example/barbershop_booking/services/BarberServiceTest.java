package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Barber;
import com.example.barbershop_booking.repositories.BarberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BarberServiceTest {

    @Mock
    private BarberRepository barberRepository;

    @InjectMocks
    private BarberService barberService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBarber() {
        Barber b = new Barber();
        b.setName("Mike");
        b.setSpecialty("Fade");
        b.setRating(4.5);

        Barber saved = new Barber(1L, "Mike", "Fade", 4.5, null);

        when(barberRepository.save(b)).thenReturn(saved);

        Barber result = barberService.createBarber(b);
        assertThat(result.getId()).isEqualTo(1L);
        verify(barberRepository, times(1)).save(b);
    }

    @Test
    void testGetAllBarbers() {
        when(barberRepository.findAll()).thenReturn(java.util.List.of());
        assertThat(barberService.getAllBarbers()).isEmpty();
    }
}

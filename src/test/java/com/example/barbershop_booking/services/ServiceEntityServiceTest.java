package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.ServiceEntity;
import com.example.barbershop_booking.repositories.ServiceEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

class ServiceEntityServiceTest {

    @Mock
    private ServiceEntityRepository serviceEntityRepository;

    @InjectMocks
    private ServiceEntityService serviceEntityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateService() {
        ServiceEntity input = new ServiceEntity(null, "Haircut", 30, 15.0);
        ServiceEntity saved = new ServiceEntity(1L, "Haircut", 30, 15.0);

        when(serviceEntityRepository.save(input)).thenReturn(saved);

        ServiceEntity result = serviceEntityService.createService(input);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Haircut");

        verify(serviceEntityRepository, times(1)).save(input);
    }

    @Test
    void testGetServiceById_Found() {
        ServiceEntity serviceEntity = new ServiceEntity(2L, "Beard Trim", 15, 10.0);
        when(serviceEntityRepository.findById(2L)).thenReturn(Optional.of(serviceEntity));

        ServiceEntity result = serviceEntityService.getServiceById(2L);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Beard Trim");
    }

    @Test
    void testGetServiceById_NotFound() {
        when(serviceEntityRepository.findById(999L)).thenReturn(Optional.empty());
        ServiceEntity result = serviceEntityService.getServiceById(999L);
        assertThat(result).isNull();
    }

    @Test
    void testGetServicesByDurationLessThan() {
        ServiceEntity s1 = new ServiceEntity(1L, "Haircut", 30, 15.0);

        when(serviceEntityRepository.findByDurationLessThan(40))
                .thenReturn(List.of(s1));

        List<ServiceEntity> result = serviceEntityService.getServicesByDurationLessThan(40);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void testGetServicesByPriceLessThan() {
        ServiceEntity s1 = new ServiceEntity(2L, "Beard Trim", 15, 10.0);

        when(serviceEntityRepository.findByPriceLessThan(20.0))
                .thenReturn(List.of(s1));

        List<ServiceEntity> result = serviceEntityService.getServicesByPriceLessThan(20.0);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
    }
}

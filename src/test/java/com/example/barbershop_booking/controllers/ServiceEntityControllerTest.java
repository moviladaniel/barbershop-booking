package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.ServiceEntity;
import com.example.barbershop_booking.entities.User;
import com.example.barbershop_booking.services.ServiceEntityService;
import com.example.barbershop_booking.repositories.UserRepository;
import com.example.barbershop_booking.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ServiceEntityController.class)
class ServiceEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceEntityService serviceEntityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateService() throws Exception {
        ServiceEntity input = new ServiceEntity(null, "Haircut", 30, 15.0);
        ServiceEntity saved = new ServiceEntity(1L, "Haircut", 30, 15.0);

        given(serviceEntityService.createService(Mockito.any(ServiceEntity.class))).willReturn(saved);

        mockMvc.perform(post("/api/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Haircut"));
    }

    @Test
    void testGetAllServices() throws Exception {
        ServiceEntity s1 = new ServiceEntity(1L, "Haircut", 30, 15.0);
        ServiceEntity s2 = new ServiceEntity(2L, "Beard Trim", 15, 10.0);

        given(serviceEntityService.getAllServices()).willReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetServicesByDurationLessThan() throws Exception {
        ServiceEntity s1 = new ServiceEntity(1L, "Haircut", 30, 15.0);

        given(serviceEntityService.getServicesByDurationLessThan(40))
                .willReturn(List.of(s1));

        mockMvc.perform(get("/api/services/byDurationLessThan")
                        .param("duration", "40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetServicesByPriceLessThan() throws Exception {
        ServiceEntity s2 = new ServiceEntity(2L, "Beard Trim", 15, 10.0);

        given(serviceEntityService.getServicesByPriceLessThan(20.0))
                .willReturn(List.of(s2));

        mockMvc.perform(get("/api/services/byPriceLessThan")
                        .param("price", "20.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L));
    }

    @Test
    void testGetUsersByFirstNameOrLastName() {
        User u1 = new User();
        u1.setId(1L);
        u1.setFirstName("John");
        u1.setLastName("Doe");

        when(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                "john", "doe"))
                .thenReturn(List.of(u1));

        List<User> result = userService.getUsersByFirstNameOrLastName("john", "doe");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }
}

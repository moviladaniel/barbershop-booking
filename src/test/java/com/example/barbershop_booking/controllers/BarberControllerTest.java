package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Barber;
import com.example.barbershop_booking.services.BarberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BarberController.class)
class BarberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarberService barberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBarber() throws Exception {
        Barber barberInput = new Barber();
        barberInput.setName("Mike");
        barberInput.setSpecialty("Fade");
        barberInput.setRating(4.5);

        Barber barberSaved = new Barber(1L, "Mike", "Fade", 4.5, null);

        given(barberService.createBarber(Mockito.any(Barber.class))).willReturn(barberSaved);

        mockMvc.perform(post("/api/barbers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(barberInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mike"));
    }
}

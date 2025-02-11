package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.services.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBookings_Empty() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/bookings"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                // expecting empty array if no data in DB
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void testGetBookingsByStatus() throws Exception {
        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setStatus("SCHEDULED");

        given(bookingService.getBookingsByStatus("schedu"))
                .willReturn(List.of(b1));

        mockMvc.perform(get("/api/bookings/byStatus")
                        .param("status", "schedu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetBookingsByBarber() throws Exception {
        Booking b1 = new Booking();
        b1.setId(3L);

        given(bookingService.getBookingsByBarber(5L)).willReturn(List.of(b1));

        mockMvc.perform(get("/api/bookings/byBarber")
                        .param("barberId", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L));
    }

}

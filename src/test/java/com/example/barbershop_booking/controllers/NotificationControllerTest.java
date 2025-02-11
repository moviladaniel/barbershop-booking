package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.*;
import com.example.barbershop_booking.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateNotification() throws Exception {
        Notification input = new Notification();
        input.setMessage("Test message");
        input.setRecipientEmail("test@example.com");

        Notification saved = new Notification(1L, "Test message", "test@example.com",
                null, false, null);

        given(notificationService.createNotification(Mockito.any(Notification.class)))
                .willReturn(saved);

        mockMvc.perform(post("/api/notifications/createNotification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Test message"));
    }

    @Test
    void testGetAllNotifications() throws Exception {
        Notification n1 = new Notification(1L, "Msg1", "a@b.com", null, true, null);
        Notification n2 = new Notification(2L, "Msg2", "c@d.com", null, false, null);

        given(notificationService.getAllNotifications()).willReturn(List.of(n1, n2));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testSendReminder() throws Exception {
        Notification reminder = new Notification(10L, "Reminder text", "john@example.com", null, true, null);
        given(notificationService.sendReminder(5L)).willReturn(reminder);

        mockMvc.perform(post("/api/notifications/sendReminder/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.message").value("Reminder text"));
    }

    @Test
    void testGetNotificationsByDelivered() throws Exception {
        Notification n1 = new Notification();
        n1.setId(5L);
        n1.setDelivered(true);

        given(notificationService.getNotificationsByDelivered(true))
                .willReturn(List.of(n1));

        mockMvc.perform(get("/api/notifications/byDelivered")
                        .param("delivered", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5L));
    }
}

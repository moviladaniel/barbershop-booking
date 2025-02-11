package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.User;
import com.example.barbershop_booking.services.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("secret");
        user.setFirstName("John");
        user.setLastName("Doe");

        User saved = new User();
        saved.setId(1L);
        saved.setEmail("john@example.com");
        saved.setPassword("secret");
        saved.setFirstName("John");
        saved.setLastName("Doe");

        given(userService.createUser(Mockito.any(User.class))).willReturn(saved);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        given(userService.getUserById(999L)).willReturn(null);

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUsersByName() throws Exception {
        User u1 = new User();
        u1.setId(10L);
        u1.setFirstName("John");
        u1.setLastName("Doe");

        given(userService.getUsersByFirstNameOrLastName("John", "Doe"))
                .willReturn(List.of(u1));

        mockMvc.perform(get("/api/users/byName")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L));
    }
}

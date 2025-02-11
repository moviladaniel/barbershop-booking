package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.User;
import com.example.barbershop_booking.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User input = new User();
        input.setEmail("test@example.com");
        input.setPassword("secret");
        input.setFirstName("Test");
        input.setLastName("User");

        User saved = new User();
        saved.setId(1L);
        saved.setEmail("test@example.com");
        saved.setPassword("secret");
        saved.setFirstName("Test");
        saved.setLastName("User");

        when(userRepository.save(input)).thenReturn(saved);

        User result = userService.createUser(input);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userRepository, times(1)).save(input);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        User user = userService.getUserById(999L);
        assertThat(user).isNull();
    }
}
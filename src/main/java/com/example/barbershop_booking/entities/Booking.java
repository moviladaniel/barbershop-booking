package com.example.barbershop_booking.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @NotBlank
    private String status;
    private boolean paid;

    // Many Bookings can belong to One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private User user;

    // Many Bookings can belong to One Barber
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
//    @JsonManagedReference
    private Barber barber;

    // Each booking can have multiple services
    @ManyToMany
    @JoinTable(
            name = "booking_services",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
//    @JsonBackReference
    private List<ServiceEntity> services = new ArrayList<>();
}


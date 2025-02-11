package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Review;
import com.example.barbershop_booking.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateReview() throws Exception {
        Review created = new Review(1L, 5, "Great service!", null, null);

        given(reviewService.createReview(100L, 5, "Great service!")).willReturn(created);

        mockMvc.perform(post("/api/reviews/100?rating=5&comment=Great service!"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void testGetReviewById() throws Exception {
        Review found = new Review(2L, 4, "Pretty good!", null, null);

        given(reviewService.getReviewById(2L)).willReturn(found);

        mockMvc.perform(get("/api/reviews/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.comment").value("Pretty good!"));
    }

    @Test
    void testGetReviewsByComment() throws Exception {
        Review r1 = new Review();
        r1.setId(10L);
        r1.setComment("Great job!");

        given(reviewService.getReviewsByComment("great"))
                .willReturn(List.of(r1));

        mockMvc.perform(get("/api/reviews/byComment")
                        .param("comment", "great"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L));
    }

    @Test
    void testGetReviewsByRating() throws Exception {
        Review r1 = new Review();
        r1.setId(11L);
        r1.setRating(5);

        given(reviewService.getReviewsByRating(4))
                .willReturn(List.of(r1));

        mockMvc.perform(get("/api/reviews/byRating")
                        .param("rating", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(11L));
    }
}

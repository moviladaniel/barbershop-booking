package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.entities.Review;
import com.example.barbershop_booking.repositories.BookingRepository;
import com.example.barbershop_booking.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview_BookingFound() {
        Booking mockBooking = new Booking();
        mockBooking.setId(100L);

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(mockBooking));

        Review savedReview = new Review(1L, 5, "Great service!", LocalDateTime.now(), mockBooking);

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        Review result = reviewService.createReview(100L, 5, "Great service!");
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testCreateReview_BookingNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        Review result = reviewService.createReview(999L, 3, "Ok...");
        assertThat(result).isNull();
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testUpdateReview() {
        Review existing = new Review(10L, 4, "Good", LocalDateTime.now(), null);
        when(reviewRepository.findById(10L)).thenReturn(Optional.of(existing));

        Review updated = new Review(10L, 5, "Excellent!", existing.getReviewDate(), null);
        when(reviewRepository.save(any(Review.class))).thenReturn(updated);

        Review result = reviewService.updateReview(10L, 5, "Excellent!");
        assertThat(result.getRating()).isEqualTo(5);
        assertThat(result.getComment()).isEqualTo("Excellent!");
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testGetReviewsByComment() {
        Review r1 = new Review();
        r1.setId(1L);
        r1.setComment("Great service!");

        when(reviewRepository.findByCommentContainingIgnoreCase("great"))
                .thenReturn(List.of(r1));

        List<Review> result = reviewService.getReviewsByComment("great");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void testGetReviewsByRating() {
        Review r1 = new Review();
        r1.setId(2L);
        r1.setRating(5);

        when(reviewRepository.findByRatingGreaterThanEqual(4))
                .thenReturn(List.of(r1));

        List<Review> result = reviewService.getReviewsByRating(4);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
    }
}

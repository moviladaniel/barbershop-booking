package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.Booking;
import com.example.barbershop_booking.entities.Review;
import com.example.barbershop_booking.repositories.BookingRepository;
import com.example.barbershop_booking.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Review createReview(Long bookingId, int rating, String comment) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return null;
        }
        Review review = new Review();
        review.setBooking(booking);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getReviewsByComment(String comment) {
        return reviewRepository.findByCommentContainingIgnoreCase(comment);
    }

    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRatingGreaterThanEqual(rating);
    }

    public Review updateReview(Long reviewId, int newRating, String newComment) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) {
            return null;
        }
        review.setRating(newRating);
        review.setComment(newComment);
        return reviewRepository.save(review);
    }

    public boolean deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            return false;
        }
        reviewRepository.deleteById(reviewId);
        return true;
    }
}

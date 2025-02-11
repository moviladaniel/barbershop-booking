package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.Review;
import com.example.barbershop_booking.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long bookingId,
            @RequestParam int rating,
            @RequestParam String comment
    ) {
        Review created = reviewService.createReview(bookingId, rating, comment);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> all = reviewService.getAllReviews();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return (review != null) ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byComment")
    public List<Review> getReviewsByComment(@RequestParam String comment) {
        return reviewService.getReviewsByComment(comment);
    }

    @GetMapping("/byRating")
    public List<Review> getReviewsByRating(@RequestParam int rating) {
        return reviewService.getReviewsByRating(rating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @RequestParam int newRating,
            @RequestParam String newComment
    ) {
        Review updated = reviewService.updateReview(id, newRating, newComment);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

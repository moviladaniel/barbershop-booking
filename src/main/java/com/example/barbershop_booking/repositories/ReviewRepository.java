package com.example.barbershop_booking.repositories;

import com.example.barbershop_booking.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCommentContainingIgnoreCase(String comment);

    List<Review> findByRatingGreaterThanEqual(int rating);
}

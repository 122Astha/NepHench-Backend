package com.example.NepHench.repository;

import com.example.NepHench.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByServiceProviderId(Integer serviceProviderId);
}

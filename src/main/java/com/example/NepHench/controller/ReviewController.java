package com.example.NepHench.controller;

import com.example.NepHench.beans.ReviewRequest;
import com.example.NepHench.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("")
    public ResponseEntity<String> submitReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.submitReview(reviewRequest);
        return ResponseEntity.ok("Review submitted successfully.");
    }
//    @GetMapping("/{serviceProviderId}")
//    public ResponseEntity<RatingRequest> getServiceProvidersRating(@PathVariable Integer serviceProviderId) {
//        RatingRequest ratingRequest = reviewService.getServiceProvidersRating(serviceProviderId);
//        return ResponseEntity.ok(ratingRequest);
//    }
}

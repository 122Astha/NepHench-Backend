package com.example.NepHench.service;

import com.example.NepHench.beans.ReviewRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<String> submitReview(ReviewRequest reviewRequest);
    String getServiceProvidersRating(Integer serviceProviderId) throws JsonProcessingException;
}

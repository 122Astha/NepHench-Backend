package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.RatingRequest;
import com.example.NepHench.beans.ReviewRequest;
import com.example.NepHench.model.Review;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.ReviewRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.ReviewService;
import com.example.NepHench.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ResponseEntity<String> submitReview(ReviewRequest reviewRequest) {

        Optional<User> customer = userRepository.findById(reviewRequest.getCustomer());
        if (customer == null) {
            return ResponseEntity.badRequest().body("Invalid customer");
        }
        Optional<User> sp = userRepository.findById(reviewRequest.getServiceProvider());
        if (sp == null) {
            return ResponseEntity.badRequest().body("Invalid service provider");
        }
        Review review = new Review();
        review.setReviewText(reviewRequest.getReviewText());
        review.setRating(reviewRequest.getRating());
        review.setCustomer(customer.get());
        review.setServiceProvider(sp.get());

        //Save the new review
        reviewRepository.save(review);

        //Update the service provider rating
        updateServiceProviderRating(reviewRequest.getServiceProvider());

        return ResponseEntity.ok("Review stored");
    }
    @Override
    public String getServiceProvidersRating(Integer serviceProviderId) {
        User serviceProvider = userRepository.findById(serviceProviderId)
                .orElseThrow(() -> new NotFoundException("ServiceProvider not found"));

        double averageRating = serviceProvider.getAverageRating();
        int totalRatings = serviceProvider.getTotalRatings();

        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setAverageRating(serviceProvider.getAverageRating() != null? serviceProvider.getAverageRating() : 0.0);
        ratingRequest.setTotalRatings(serviceProvider.getTotalRatings());

        return ratingRequest.toString();
    }

    private void updateServiceProviderRating(Integer serviceProviderId) {
        User serviceProvider = userRepository.findById(serviceProviderId)
                .orElseThrow(() -> new NotFoundException("Service provider not found."));

        List<Review> reviews = reviewRepository.findByServiceProviderId(serviceProviderId);

        int totalRatings = reviews.size();
        double sumRatings = reviews.stream().mapToDouble(Review::getRating).sum();
        double averageRating = totalRatings > 0 ? sumRatings / totalRatings : 0;

        serviceProvider.setTotalRatings(totalRatings);
        serviceProvider.setAverageRating(averageRating);

        userRepository.save(serviceProvider);
    }
}

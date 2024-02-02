package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.UpdateProfileRequest;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable Integer userId, @RequestBody UpdateProfileRequest request) throws IOException {
        // Extract the image Base64 string from the request
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        String rolename = user.getRole().getName();
        switch (rolename) {
            case "Customer" -> {
                byte[] imageData = Base64.getDecoder().decode(request.getImage());
                user.setImage(imageData);
                user.setUsername(request.getUsername());
                user.setAddress(request.getAddress());
            }
            case "Service Provider" -> {
                byte[] imageData = Base64.getDecoder().decode(request.getImage());
                byte[] frontImage =  request.getFrontImage().getBytes();
                byte[] backImage = request.getBackImage().getBytes();

                user.setImage(imageData);
                user.setUsername(request.getUsername());
                user.setAddress(request.getAddress());
                user.setLicenseNumber(request.getLiscenceNo());
                user.setBackImage(backImage);
                user.setFrontImage(frontImage);
                user.setPhone(request.getPhone());
                user.setUserdescp(request.getUserdescp());
            }
        }
        // Update the user profile with other details
        userRepository.save(user);
        ApiResponse response = new ApiResponse("Profile updated successfully");
        return ResponseEntity.ok(response);
    }
}

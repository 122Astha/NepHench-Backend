package com.example.NepHench.controller;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.ResetPassword;
import com.example.NepHench.beans.ResetRequest;
import com.example.NepHench.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reset-password")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody ResetRequest resetRequest) {
        passwordResetService.initiatePasswordReset(resetRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPassword resetPassword) {
        passwordResetService.resetPassword(resetPassword.getToken(), resetPassword.getNewPassword());
        ApiResponse response = new ApiResponse("Password changed successfully");
        return ResponseEntity.ok(response);
    }
}

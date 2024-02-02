package com.example.NepHench.service;

import com.example.NepHench.exception.InvalidTokenException;
import com.example.NepHench.exception.NotFoundException;
import com.example.NepHench.model.PasswordResetToken;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.PasswordResetTokenRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional == null) {
            throw new NotFoundException("User not found");
        }else {
            User user = userOptional.get();


        String token = OTPGenerator.generateOTP();
        Instant expiryDate = Instant.now().plusSeconds(300);
        Instant created_at = Instant.now();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetToken.setCreatedAt(created_at);

        tokenRepository.save(passwordResetToken);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
        }
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);

        if (passwordResetToken == null || passwordResetToken.isUsed() || passwordResetToken.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        User user = passwordResetToken.getUser();
        user.setPassword(encodePassword(newPassword));

        userRepository.save(user);

        passwordResetToken.setUsed(true);
        tokenRepository.save(passwordResetToken);
    }


    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}


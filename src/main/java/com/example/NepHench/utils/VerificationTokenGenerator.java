package com.example.NepHench.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class VerificationTokenGenerator {
    private static final int TOKEN_LENGTH = 32; // Length of the verification token

    public static String generateVerificationToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}

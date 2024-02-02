package com.example.NepHench.utils;

import java.util.Random;

public class OTPGenerator {
    private static final int OTP_LENGTH = 6; // Set the desired length of the OTP code

    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generates a random digit from 0 to 9
        }

        return otp.toString();
    }
}


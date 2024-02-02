package com.example.NepHench.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendVerificationEmail(String recipientEmail, String verificationToken) {
        String subject = "Verify Your Email";
        String verificationLink = "http://192.168.1.69:9096/api/users/verify-email?token=" + verificationToken;

        String body = "<html><body>"
                + "<h2>Please verify your email:</h2>"
                + "<p>Click the button below to verify your email:</p>"
                + "<a href=\"" + verificationLink + "\">"
                + "<button style=\"background-color:#4CAF50;color:white;padding:14px 20px;"
                + "border:none;text-align:center;text-decoration:none;display:inline-block;"
                + "font-size:16px;margin:4px 2px;cursor:pointer;\">Verify Email</button>"
                + "</a>"
                + "</body></html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try{
//            SimpleMailMessage message = new SimpleMailMessage();
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // Use true to enable HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
        }

    }

    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset Request";
        String otp = token;

        String body = "<html><body>"
                + "<p>Dear User, "+ token + " is your Nephench OTP. OTP is valid for 5 minutes only.</p></br>"
                + "<p>For security, don't share it with anyone.</p></br>"
                + "<p>Best Regards, Nephench</p>"
                + "</body></html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try{
//            SimpleMailMessage message = new SimpleMailMessage();
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true); // Use true to enable HTML content
            emailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
        }
    }
}

package com.example.NepHench.serviceImpl;

public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException(String message) {
        super(message);
    }
}

package com.example.NepHench.service;

import com.example.NepHench.beans.AmountRequest;
import org.springframework.http.ResponseEntity;

public interface AmountService {
    ResponseEntity<String> submitAmount(AmountRequest amountRequest);
}

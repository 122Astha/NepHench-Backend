package com.example.NepHench.controller;

import com.example.NepHench.beans.AmountRequest;
import com.example.NepHench.service.AmountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cashinhand")
public class AmountController {
    private final AmountService amountService;

    public AmountController(AmountService amountService) {
        this.amountService = amountService;
    }
    @PostMapping("")
    public ResponseEntity<String> submitAmount(@RequestBody AmountRequest amountRequest) {
        amountService.submitAmount(amountRequest);
        return ResponseEntity.ok("Amount submitted successfully.");
    }
}

package com.example.NepHench.controller;

import com.example.NepHench.beans.InvoiceRequest;
import com.example.NepHench.model.Invoice;
import com.example.NepHench.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {
    private final BillingService billingService;

    public InvoiceController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try{
            Invoice invoice = billingService.createInvoice(invoiceRequest.getCustomerId(), invoiceRequest.getServiceId());
            return ResponseEntity.ok(invoice);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}



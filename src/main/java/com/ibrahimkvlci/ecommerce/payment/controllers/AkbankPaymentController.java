package com.ibrahimkvlci.ecommerce.payment.controllers;

import com.ibrahimkvlci.ecommerce.payment.dto.AkbankPaymentResultRequest;
import com.ibrahimkvlci.ecommerce.payment.services.AkbankPaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/akbank")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AkbankPaymentController {

    private final AkbankPaymentService akbankPaymentService;

    @PostMapping("/okCheckout")
    public ResponseEntity<String> okCheckout(@ModelAttribute AkbankPaymentResultRequest paymentResult) {
        akbankPaymentService.okCheckout(paymentResult);
        return ResponseEntity.ok("Payment result received successfully");
    }

    @PostMapping("/failCheckout")
    public ResponseEntity<String> failCheckout(@ModelAttribute AkbankPaymentResultRequest paymentResult) {
        akbankPaymentService.failCheckout(paymentResult);
        return ResponseEntity.ok(paymentResult.getHostMessage());
    }
}

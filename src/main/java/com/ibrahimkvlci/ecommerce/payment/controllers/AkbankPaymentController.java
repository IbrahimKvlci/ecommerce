package com.ibrahimkvlci.ecommerce.payment.controllers;

import com.ibrahimkvlci.ecommerce.payment.dto.AkbankPaymentResultRequest;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentIncorrectValuesError;
import com.ibrahimkvlci.ecommerce.payment.services.AkbankPaymentService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/akbank")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AkbankPaymentController {

    private final AkbankPaymentService akbankPaymentService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @PostMapping("/okCheckout")
    public void okCheckout(@ModelAttribute AkbankPaymentResultRequest paymentResult, HttpServletResponse response)
            throws IOException {
        try {
            akbankPaymentService.okCheckout(paymentResult);
        } catch (PaymentIncorrectValuesError e) {
            response.sendRedirect(frontendUrl + "/checkout/fail?error=" + paymentResult.getHostMessage());
        } catch (Exception e) {
            response.sendRedirect(frontendUrl + "/checkout/success");
        }
        response.sendRedirect(frontendUrl + "/checkout/success");
    }

    @PostMapping("/failCheckout")
    public void failCheckout(@ModelAttribute AkbankPaymentResultRequest paymentResult, HttpServletResponse response)
            throws IOException {
        String encodedMsg = "";
        try {
            encodedMsg = URLEncoder.encode(paymentResult.getHostMessage(), StandardCharsets.UTF_8);
            akbankPaymentService.failCheckout(paymentResult);
        } catch (Exception e) {
            response.sendRedirect(frontendUrl + "/checkout/fail?error=" + encodedMsg);
        }
        response.sendRedirect(frontendUrl + "/checkout/fail?error=" + encodedMsg);
    }
}

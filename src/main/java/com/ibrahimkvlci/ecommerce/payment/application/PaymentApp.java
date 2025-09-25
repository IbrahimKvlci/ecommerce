package com.ibrahimkvlci.ecommerce.payment.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.payment.dto.CardCheckDTO;
import com.ibrahimkvlci.ecommerce.payment.services.PaymentService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentApp {


    private final PaymentService paymentService;

    public String payCheck(CardCheckDTO cardCheckDTO){
        return paymentService.payCheck(cardCheckDTO);
    }
}

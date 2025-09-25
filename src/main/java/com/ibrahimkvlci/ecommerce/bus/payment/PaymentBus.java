package com.ibrahimkvlci.ecommerce.bus.payment;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.payment.application.PaymentApp;
import com.ibrahimkvlci.ecommerce.payment.dto.CardCheckDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentBus {

    private final PaymentApp paymentApp;

    public String payCheck(CardCheckDTO cardCheckDTO){
        return paymentApp.payCheck(cardCheckDTO);
    }
}

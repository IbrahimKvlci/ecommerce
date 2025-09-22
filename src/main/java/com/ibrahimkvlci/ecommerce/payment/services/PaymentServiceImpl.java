package com.ibrahimkvlci.ecommerce.payment.services;

import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.payment.client.PaymentGatewayClient;
import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentGatewayClient paymentGatewayClient;

    @Override
    public String payCheck(PaymentDTO paymentDTO) {
        return paymentGatewayClient.cardCheck(p)
    }

    private String xmlUtil(){
        
    }

}

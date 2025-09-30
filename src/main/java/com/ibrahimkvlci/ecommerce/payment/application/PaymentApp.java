package com.ibrahimkvlci.ecommerce.payment.application;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.payment.services.PaymentService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentApp {


    private final PaymentService paymentService;

    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException, InvalidKeyException{
        return paymentService.sale(saleRequest);
    }

    public SaleResponse sale3DPay(SaleRequest saleRequest){
        return paymentService.sale3DPay(saleRequest);
    }
}

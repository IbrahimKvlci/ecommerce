package com.ibrahimkvlci.ecommerce.bus.payment;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.payment.application.PaymentApp;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentAppBus {

    private final PaymentApp paymentApp;

    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException,InvalidKeyException{
        return paymentApp.sale(saleRequest);
    }

    public SaleResponse sale3DPay(SaleRequest saleRequest){
        return paymentApp.sale3DPay(saleRequest);
    }

}

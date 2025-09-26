package com.ibrahimkvlci.ecommerce.order.client;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentClientModularMonolith implements PaymentClient{

    private final OrderBus paymentBus;

    @Override
    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException,InvalidKeyException {
        return paymentBus.sale(saleRequest);
    }

}

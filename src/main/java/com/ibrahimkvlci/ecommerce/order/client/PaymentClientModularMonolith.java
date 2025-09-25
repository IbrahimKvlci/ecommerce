package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.CardCheckDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentClientModularMonolith implements PaymentClient{

    private final OrderBus paymentBus;

    @Override
    public String payCheck(CardCheckDTO cardCheckDTO) {
        return paymentBus.payCheck(cardCheckDTO);
    }

}

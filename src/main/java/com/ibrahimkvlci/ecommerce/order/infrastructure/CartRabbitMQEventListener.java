package com.ibrahimkvlci.ecommerce.order.infrastructure;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.order.infrastructure.config.RabbitMQOrderConfig;
import com.ibrahimkvlci.ecommerce.order.services.CartService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartRabbitMQEventListener {

    private final CartService cartService;

    @RabbitListener(queues = RabbitMQOrderConfig.ORDER_CUSTOMER_CREATED_QUEUE)
    public void handleCustomerCreatedEvent(CustomerDTO event) {
        cartService.createCart(new CreateCartRequest(event.getId()));
    }
}

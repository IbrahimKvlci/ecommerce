package com.ibrahimkvlci.ecommerce.auth.infrastructure.adapters;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.auth.services.UserEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQUserEventPublisher implements UserEventPublisher {

    @Value("${message-broker.customer-created}")
    private String customerCreated;

    @Value("${message-broker.domain-exchange}")
    public String domainExchange;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishCustomerCreated(CustomerDTO customer) {
        rabbitTemplate.convertAndSend(domainExchange, customerCreated, customer);
    }

}
package com.ibrahimkvlci.ecommerce.order.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQOrderConfig {

    public static final String ORDER_CUSTOMER_CREATED_QUEUE = "order.customer.created.queue";
    public static final String CUSTOMER_CREATED = "customer.created";

    @Bean
    public Queue orderCustomerQueue() {
        return new Queue(ORDER_CUSTOMER_CREATED_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange domainExchange) {
        return BindingBuilder.bind(queue)
                .to(domainExchange)
                .with(CUSTOMER_CREATED);
    }
}

package com.ibrahimkvlci.ecommerce.shared.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSharedConfig {

    @Value("${message-broker.domain-exchange}")
    public String domainExchange;

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(domainExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

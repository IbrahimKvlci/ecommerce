package com.ibrahimkvlci.ecommerce.payment.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class KuveytTurkPaymentGatewayClient implements PaymentGatewayClient {

    @Value("${kuveyt.turk.card.check.url}")
    private String cardCheckUrl;

    @Value("${kuveyt.turk.pay.url}")
    private String payUrl;

    @Override
    public String cardCheck(String xmlString) {
        RestClient restClient = RestClient.create();

        ResponseEntity<String> response = restClient.post()
                .uri(Objects.requireNonNull(cardCheckUrl))
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_XML))
                .body(Objects.requireNonNull(xmlString))
                .retrieve()
                .toEntity(String.class);

        return response.getBody();
    }

    @Override
    public String pay(String xmlString) {
        RestClient restClient = RestClient.create();

        ResponseEntity<String> response = restClient.post()
                .uri(Objects.requireNonNull(payUrl))
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_XML))
                .body(Objects.requireNonNull(xmlString))
                .retrieve()
                .toEntity(String.class);

        return response.getBody();
    }

}

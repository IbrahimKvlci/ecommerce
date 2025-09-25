package com.ibrahimkvlci.ecommerce.payment.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.payment.client.PaymentGatewayClient;
import com.ibrahimkvlci.ecommerce.payment.dto.CardCheckDTO;
import com.ibrahimkvlci.ecommerce.payment.utils.KuveytTurkUtil.KuveytTurkXmlUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentGatewayClient paymentGatewayClient;
    private final KuveytTurkXmlUtil kuveytTurkXmlUtil;

    @Value("${kuveyt.turk.card.check.url.ok}")
    private String cardCheckOkUrl;

    @Value("${kuveyt.turk.card.check.url.fail}")
    private String cardCheckFailUrl;

    @Value("${kuveyt.turk.merchantid}")
    private String merchantId;

    @Value("${kuveyt.turk.username}")
    private String username;

    @Value("${kuveyt.turk.password}")
    private String password;

    @Override
    public String payCheck(CardCheckDTO cardCheckDTO) {

        String hashed_password = java.util.Base64.getEncoder().encodeToString(
            java.security.MessageDigest.getInstance("SHA-1")
                .digest(password.getBytes(java.nio.charset.Charset.forName("ISO-8859-9")))
        );

        String dataToHash = merchantId
                + cardCheckDTO.getOrderNumber()
                + cardCheckDTO.getOrderAmount()
                + cardCheckOkUrl
                + cardCheckFailUrl
                + username
                + hashed_password;
        String hashData = java.util.Base64.getEncoder().encodeToString(
                java.security.MessageDigest.getInstance("SHA-1")
                        .digest(dataToHash.getBytes(java.nio.charset.Charset.forName("ISO-8859-9")))
        );

        String xmlString = kuveytTurkXmlUtil.cardCheckXmlPost(
        cardCheckOkUrl,
        cardCheckFailUrl,
        hashData,
        cardCheckDTO.getCardNumber(),
        cardCheckDTO.getExpirationDateYear(),
        cardCheckDTO.getExpirationDateYMonth(),
        cardCheckDTO.getCvv(),
        cardCheckDTO.getCardHolderName(),
        "VISA",
        cardCheckDTO.getOrderAmount(),
        cardCheckDTO.getOrderAmount(), // displayAmount
        cardCheckDTO.getOrderNumber(),
        null,//device channel
        cardCheckDTO.getClientIp(),
        null, // billAddrCity
        null, // billAddrCountry
        null, // billAddrLine1
        null, // BillAddrPostCode
        null, // billAddrState
        null, // email
        null, // cc
        null  // Subscriber

        );
        return paymentGatewayClient.cardCheck(xmlString);
    }


}

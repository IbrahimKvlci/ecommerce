package com.ibrahimkvlci.ecommerce.payment.services;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

@Service
public class AkbankPaymentService implements PaymentService{


    @Value("${akbank.merchantUser}")
    private String merchantUser;

    @Value("${akbank.merchantPassword}")
    private String merchantPassword;

    @Value("${akbank.secretKey}")
    private String secretKey;

    @Value("${akbank.sale.url}")
    private String saleUrl;

    @Override
    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException, InvalidKeyException{

        String expireMonth = String.format("%02d", saleRequest.getCardInfoDTO().getExpirationDateMonth());
        String expireYear = String.valueOf(saleRequest.getCardInfoDTO().getExpirationDateYear());
        if (expireYear.length() > 2) {
            expireYear = expireYear.substring(expireYear.length() - 2);
        }

        String json = "{"
            +"\"version\": \"1.00\","
            +"\"txnCode\": \""+ 1000 + "\","
            +"\"requestDateTime\": \""+ java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(java.time.LocalDateTime.now()) + "\","
            +"\"randomNumber\": \"" + getRandomNumberBase16(128) + "\","
            +"\"terminal\" :{"
            +"\"merchantSafeId\": \"" + merchantUser + "\","
            +"\"terminalSafeId\": \"" + merchantPassword + "\""
            +"},"
            +"\"card\": {"
            +"\"cardNumber\": \"" + saleRequest.getCardInfoDTO().getCardNumber() + "\","
            +"\"cvv2\": \"" +  saleRequest.getCardInfoDTO().getCvv() + "\","
            +"\"expireDate\": \"" + expireMonth + expireYear + "\""
            +"},"
            +"\"reward\": {"
            +"\"ccbRewardAmount\": 0,"
            +"\"pcbRewardAmount\": 0,"
            +"\"xcbRewardAmount\": 0"
            +"},"
            +"\"transaction\": {"
            +"\"amount\":"  + saleRequest.getOrderDTO().getAmount() + ","
            +"\"currencyCode\":"  + saleRequest.getOrderDTO().getCurrencyCode() + ","
            +"\"motoInd\":"  + 0 + ","
            +"\"installCount\":"  + saleRequest.getOrderDTO().getInstallCount()
            +"},"
            +"\"customer\": {"
            +"\"emailAddress\": \"" + saleRequest.getCustomerDTO().getEmailAddress() + "\","
            +"\"ipAddress\": \"" + saleRequest.getCustomerDTO().getIpAddress() + "\""
            +"}"
            +"}";
    String hash = hashToString(json, secretKey);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    httpHeaders.add("auth-hash",hash);
    HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
    SaleResponse response = new RestTemplate().exchange(saleUrl, HttpMethod.POST, entity, SaleResponse.class).getBody();
    return response; 

    }
    
    public static String getRandomNumberBase16(int length){
        StringBuilder sb = new StringBuilder();
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        String processId = processName.split("@")[0];
        String secret = processId + Thread.currentThread().threadId() + new Date().getTime();
        SecureRandom srandom = new SecureRandom(secret.getBytes());
        for(int i = 0; i < length; i++){
          sb.append(Integer.toString(srandom.nextInt(16),16));
        }
        return sb.toString();
    }
    
    public static String hashToString(String serializedModel, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretkey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(secretkey);
        byte[] hashByteArray = hmacSha512.doFinal(serializedModel.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashByteArray);
    }
    
}

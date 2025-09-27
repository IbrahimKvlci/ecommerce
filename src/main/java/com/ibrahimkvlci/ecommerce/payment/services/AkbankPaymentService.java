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

        if(!saleRequest.getCardInfoDTO().getExpirationDateMonth().matches("^\\d{2}$")){
            throw new IllegalArgumentException("Expiration Date Month is not correctly formatted! Correct: 00-12");
        }
        if(!saleRequest.getCardInfoDTO().getExpirationDateMonth().matches("^\\d{2}$")){
            throw new IllegalArgumentException("Expiration Date Year is not correctly formatted! Correct: 00-99");
        }
        String expireMonth = saleRequest.getCardInfoDTO().getExpirationDateMonth();
        String expireYear = saleRequest.getCardInfoDTO().getExpirationDateYear();

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
            +"\"emailAddress\": \"" + "test@akbank.com" + "\","
            +"\"ipAddress\": \"" + "192.168.1.1" + "\""
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

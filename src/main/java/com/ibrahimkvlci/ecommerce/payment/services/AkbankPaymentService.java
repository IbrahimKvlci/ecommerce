package com.ibrahimkvlci.ecommerce.payment.services;

import java.lang.reflect.Field;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ibrahimkvlci.ecommerce.payment.client.CheckoutClient;
import com.ibrahimkvlci.ecommerce.payment.dto.AkbankPaymentResultRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.CardInfoDTO;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.payment.exceptions.PaymentIncorrectValuesError;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class AkbankPaymentService implements PaymentService{


    @Value("${akbank.merchantUser}")
    private String merchantUser;

    @Value("${akbank.merchantPassword}")
    private String merchantPassword;

    @Value("${akbank.secretKey}")
    private String secretKey;

    @Value("${akbank.sale.url}")
    private String saleUrl;

    @Value("${akbank.securepay.url}")
    private String securePayUrl;

    @Value("${akbank.okUrl}")
    private String okUrl;

    @Value("${akbank.failUrl}")
    private String failUrl;

    @Value("${akbank.lang:TR}")
    private String lang;

    @Lazy
    private final CheckoutClient checkoutClient;

    @Override
    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException, InvalidKeyException{

        checkCardInformations(saleRequest.getCardInfoDTO());
        
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
            +"\"order\": {"
            +"\"orderId\": \"" + saleRequest.getOrderDTO().getOrderNumber() + "\""
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
            +"\"emailAddress\": \"" + saleRequest.getCustomerDTO().getEmailAddress() + "\""
            +"}"
            +"}";
    String hash = hashToString(json, secretKey);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    httpHeaders.add("auth-hash",hash);
    HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
    SaleResponse response = new RestTemplate().exchange(saleUrl, HttpMethod.POST, entity, SaleResponse.class).getBody();
    switch (response.getHostResponseCode()) {
        case "00":
            response.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
            break;
        default:
            response.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
            break;
    }
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

    @Override
    public SaleResponse sale3DPay(SaleRequest saleRequest) {
        checkCardInformations(saleRequest.getCardInfoDTO());
        String expireMonth = saleRequest.getCardInfoDTO().getExpirationDateMonth();
        String expireYear = saleRequest.getCardInfoDTO().getExpirationDateYear();

        String randomNumber = getRandomNumberBase16(128);
        String requestDateTime = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(java.time.LocalDateTime.now());

        String orderId = saleRequest.getOrderDTO().getOrderNumber();
        String amount = String.format("%.2f", saleRequest.getOrderDTO().getAmount());
        String currencyCode = Integer.toString(saleRequest.getOrderDTO().getCurrencyCode());
        String installCount = Integer.toString(saleRequest.getOrderDTO().getInstallCount());
        // Build a serialized string for hash calculation as per Akbank docs
        String hashItems =
            "3D_PAY" + // paymentModel
            "3000" + // txnCode
            merchantUser + // merchantSafeId
            merchantPassword + // terminalSafeId
            saleRequest.getOrderDTO().getOrderNumber() + // orderId
            lang + // lang
            amount + // amount
            "0.00" + // ccbRewardAmount
            "0.00" + // pcbRewardAmount
            "0.00" + // xcbRewardAmount
            currencyCode + // currencyCode
            installCount + // installCount
            okUrl + // okUrl
            failUrl + // failUrl
            saleRequest.getCustomerDTO().getEmailAddress() + // emailAddress
            saleRequest.getCardInfoDTO().getCardNumber() + // creditCard
            (expireMonth + expireYear) + // expiredDate
            saleRequest.getCardInfoDTO().getCvv() + // cvv
            saleRequest.getCardInfoDTO().getCardHolderName() + // cardHolderName
            randomNumber + // randomNumber
            requestDateTime; // requestDateTime
        String hash;
        try {
            hash = hashToString(hashItems, secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Hash generation failed", e);
        }



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("paymentModel", "3D_PAY");
        map.add("txnCode", "3000");
        map.add("merchantSafeId", merchantUser);
        map.add("terminalSafeId", merchantPassword);
        map.add("orderId", orderId);
        map.add("lang", lang);
        map.add("amount", amount);
        map.add("ccbRewardAmount", "0.00");
        map.add("pcbRewardAmount", "0.00");
        map.add("xcbRewardAmount", "0.00");
        map.add("currencyCode", currencyCode);
        map.add("installCount", installCount);
        map.add("okUrl", okUrl);
        map.add("failUrl", failUrl);
        map.add("emailAddress", saleRequest.getCustomerDTO().getEmailAddress());
        map.add("creditCard", saleRequest.getCardInfoDTO().getCardNumber());
        map.add("expiredDate", expireMonth + expireYear);
        map.add("cvv", saleRequest.getCardInfoDTO().getCvv());
        map.add("cardHolderName", saleRequest.getCardInfoDTO().getCardHolderName());
        map.add("randomNumber", randomNumber);
        map.add("requestDateTime", requestDateTime);
        map.add("hash", hash);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
        String requestString = new RestTemplate().postForObject(securePayUrl, request, String.class);
        SaleResponse response=new SaleResponse();
        response.setHtmlResponse(requestString);
        response.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
        response.setHostResponseCode("3000");
        response.setOrder(new SaleResponse.Order(orderId));
        return response;
    }

    private void checkCardInformations(CardInfoDTO cardInfoDTO){
        if(!cardInfoDTO.getExpirationDateMonth().matches("^\\d{2}$")){
            throw new IllegalArgumentException("Expiration Date Month is not correctly formatted! Correct: 00-12");
        }
        if(!cardInfoDTO.getExpirationDateYear().matches("^\\d{2}$")){
            throw new IllegalArgumentException("Expiration Date Year is not correctly formatted! Correct: 00-99");
        }
    }

    @Override
    public void okCheckout(AkbankPaymentResultRequest akbankPaymentResultRequest) {
        String[] paramNames=akbankPaymentResultRequest.getHashParams().split("\\+");
        String hashValues="";
        Class cls=akbankPaymentResultRequest.getClass();

        for (String paramName : paramNames) {
            try {
                Field field=cls.getDeclaredField(paramName);
                field.setAccessible(true);
                hashValues+=field.get(akbankPaymentResultRequest);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                
            }
            
        }
        String hashedValues;

        try {
            hashedValues=hashToString(hashValues, secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Hash generation failed", e);
        }

        if(!hashedValues.equals(akbankPaymentResultRequest.getHash())){
            throw new PaymentIncorrectValuesError("The payment values are incorrect!");
        }

        //SEND SUCCESS RESTULT TO ORDER MODULE 
        SaleResponse saleResponse=new SaleResponse();
        saleResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
        saleResponse.setHostMessage(akbankPaymentResultRequest.getHostMessage());
        saleResponse.setHostResponseCode(akbankPaymentResultRequest.getHostResponseCode());
        saleResponse.setResponseMessage(akbankPaymentResultRequest.getResponseMessage());
        saleResponse.setOrder(new SaleResponse.Order(akbankPaymentResultRequest.getOrderId()));

        checkoutClient.okCheckout(saleResponse);

    }

    @Override
    public void failCheckout(AkbankPaymentResultRequest akbankPaymentResultRequest) {
        
    }
}

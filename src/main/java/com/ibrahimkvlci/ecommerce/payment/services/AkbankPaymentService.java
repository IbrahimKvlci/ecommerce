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

import com.ibrahimkvlci.ecommerce.payment.dto.CardInfoDTO;
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

    @Value("${akbank.securepay.url}")
    private String securePayUrl;

    @Value("${akbank.okUrl}")
    private String okUrl;

    @Value("${akbank.failUrl}")
    private String failUrl;

    @Value("${akbank.lang:TR}")
    private String lang;

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

        // Build a serialized string for hash calculation as per Akbank docs
        String hashItems =
            "3D_PAY" + // paymentModel
            "3000" + // txnCode
            merchantUser + // merchantSafeId
            merchantPassword + // terminalSafeId
            saleRequest.getOrderDTO().getOrderNumber() + // orderId
            lang + // lang
            saleRequest.getOrderDTO().getAmount() + // amount
            "0.00" + // ccbRewardAmount
            "0.00" + // pcbRewardAmount
            "0.00" + // xcbRewardAmount
            saleRequest.getOrderDTO().getCurrencyCode() + // currencyCode
            saleRequest.getOrderDTO().getInstallCount() + // installCount
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

        String orderId = saleRequest.getOrderDTO().getOrderNumber();
        String amount = saleRequest.getOrderDTO().getAmount();
        String currencyCode = Integer.toString(saleRequest.getOrderDTO().getCurrencyCode());
        String installCount = Integer.toString(saleRequest.getOrderDTO().getInstallCount());

        StringBuilder html = new StringBuilder();
        html.append("<html><head></head><body>")
            .append("<form id=\"akbank3d\" action=\"").append(securePayUrl).append("\" method=\"POST\">")
            .append(hidden("paymentModel","3D_PAY"))
            .append(hidden("txnCode","3000"))
            .append(hidden("merchantSafeId", merchantUser))
            .append(hidden("terminalSafeId", merchantPassword))
            .append(hidden("orderId", orderId))
            .append(hidden("lang", lang))
            .append(hidden("amount", amount))
            .append(hidden("ccbRewardAmount", "0.00"))
            .append(hidden("pcbRewardAmount", "0.00"))
            .append(hidden("xcbRewardAmount", "0.00"))
            .append(hidden("currencyCode", currencyCode))
            .append(hidden("installCount", installCount))
            .append(hidden("okUrl", okUrl))
            .append(hidden("failUrl", failUrl))
            .append(hidden("emailAddress", saleRequest.getCustomerDTO().getEmailAddress()))
            .append(hidden("creditCard", saleRequest.getCardInfoDTO().getCardNumber()))
            .append(hidden("expiredDate", expireMonth + expireYear))
            .append(hidden("cvv", saleRequest.getCardInfoDTO().getCvv()))
            .append(hidden("cardHolderName", saleRequest.getCardInfoDTO().getCardHolderName()))
            .append(hidden("randomNumber", randomNumber))
            .append(hidden("requestDateTime", requestDateTime))
            .append(hidden("hash", hash))
            .append("<noscript><input type=\"submit\" value=\"Continue\"></noscript>")
            .append("</form>")
            .append("<script>document.getElementById('akbank3d').submit();</script>")
            .append("</body></html>");

        SaleResponse response = new SaleResponse();
        response.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
        response.setResponseMessage(html.toString());
        SaleResponse.Order order = new SaleResponse.Order();
        order.setOrderId(orderId);
        response.setOrder(order);
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

    private static String hidden(String name, String value){
        return new StringBuilder()
            .append("<input type=\"hidden\" name=\"")
            .append(escapeHtml(name))
            .append("\" value=\"")
            .append(escapeHtml(value == null ? "" : value))
            .append("\">")
            .toString();
    }

    private static String escapeHtml(String input){
        if(input == null){
            return "";
        }
        return input
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }
    
}

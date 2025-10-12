package com.ibrahimkvlci.ecommerce.bus.order;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.payment.PaymentAppBus;
import com.ibrahimkvlci.ecommerce.order.client.PaymentClient;
import com.ibrahimkvlci.ecommerce.order.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderPaymentClientBus implements PaymentClient {

    private final PaymentAppBus paymentBus;

    @Override
    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException,InvalidKeyException{
        var saleRequestNew=new com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest();
        // Convert and set fields for saleRequestNew using compatible types
        var cardCheckDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CardInfoDTO(
            saleRequest.getCardCheckDTO().getCardNumber(),
            saleRequest.getCardCheckDTO().getCardHolderName(),
            saleRequest.getCardCheckDTO().getExpirationDateYear(),
            saleRequest.getCardCheckDTO().getExpirationDateMonth(),
            saleRequest.getCardCheckDTO().getCvv()
        );

        var orderDTO = new com.ibrahimkvlci.ecommerce.payment.dto.OrderDTO(
            saleRequest.getOrderDTO().getOrderNumber(),
            saleRequest.getOrderDTO().getTotalAmount(),
            saleRequest.getOrderDTO().getCurrencyCode(),
            saleRequest.getOrderDTO().getInstallCount()
        );

        var customerDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CustomerDTO(
            saleRequest.getCustomerDTO().getId(),
            saleRequest.getCustomerDTO().getEmail(),
            saleRequest.getCustomerDTO().getIpAddress()
        );

        saleRequestNew.setCardInfoDTO(cardCheckDTO);
        saleRequestNew.setOrderDTO(orderDTO);
        saleRequestNew.setCustomerDTO(customerDTO);
        com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse saleResponse=paymentBus.sale(saleRequestNew);

        SaleResponse saleResponseNew=new SaleResponse();
        switch (saleResponse.getSaleStatusEnum()) {
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Error:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Success:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectURL:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectURL);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectHTML:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
                break;
            default:
                saleResponseNew.setSaleStatusEnum(null);
        }
        saleResponseNew.setHostMessage(saleResponse.getHostMessage());
        saleResponseNew.setHostResponseCode(saleResponse.getHostResponseCode());
        saleResponseNew.setResponseMessage(saleResponse.getResponseMessage());
        SaleResponse.Order order=new SaleResponse.Order();
        order.setOrderId(saleResponse.getOrder().getOrderId());
        saleResponseNew.setOrder(order);
        return saleResponseNew;
    }

    @Override
    public SaleResponse sale3DPay(SaleRequest saleRequest){
        var saleRequestNew=new com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest();
        var cardCheckDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CardInfoDTO(
            saleRequest.getCardCheckDTO().getCardNumber(),
            saleRequest.getCardCheckDTO().getCardHolderName(),
            saleRequest.getCardCheckDTO().getExpirationDateYear(),
            saleRequest.getCardCheckDTO().getExpirationDateMonth(),
            saleRequest.getCardCheckDTO().getCvv()
        );

        var orderDTO = new com.ibrahimkvlci.ecommerce.payment.dto.OrderDTO(
            saleRequest.getOrderDTO().getOrderNumber(),
            saleRequest.getOrderDTO().getTotalAmount(),
            saleRequest.getOrderDTO().getCurrencyCode(),
            saleRequest.getOrderDTO().getInstallCount()
        );

        var customerDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CustomerDTO(
            saleRequest.getCustomerDTO().getId(),
            saleRequest.getCustomerDTO().getEmail(),
            saleRequest.getCustomerDTO().getIpAddress()
        );

        saleRequestNew.setCardInfoDTO(cardCheckDTO);
        saleRequestNew.setOrderDTO(orderDTO);
        saleRequestNew.setCustomerDTO(customerDTO);
        com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse saleResponse=paymentBus.sale3DPay(saleRequestNew);

        SaleResponse saleResponseNew=new SaleResponse();
        switch (saleResponse.getSaleStatusEnum()) {
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Error:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Success:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectURL:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectURL);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectHTML:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
                break;
            default:
                saleResponseNew.setSaleStatusEnum(null);
        }
        saleResponseNew.setHostMessage(saleResponse.getHostMessage());
        saleResponseNew.setHostResponseCode(saleResponse.getHostResponseCode());
        saleResponseNew.setResponseMessage(saleResponse.getResponseMessage());
        saleResponseNew.setHtmlResponse(saleResponse.getHtmlResponse());
        SaleResponse.Order order=new SaleResponse.Order();
        if(saleResponse.getOrder()!=null){
            order.setOrderId(saleResponse.getOrder().getOrderId());
        }
        saleResponseNew.setOrder(order);
        return saleResponseNew;
    }

}

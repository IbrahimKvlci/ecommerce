package com.ibrahimkvlci.ecommerce.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SaleResponse {

    public static enum SaleStatusEnum{
        Error,
        Success,
        RedirectURL,
        RedirectHTML,
    }

    private SaleStatusEnum saleStatusEnum;

    private String hostMessage;

    private String hostResponseCode;

    private String responseMessage;

    private Order order;

    private String htmlResponse;

    @Data
    @AllArgsConstructor
    public static class Order{

        private String orderId;
    }

}

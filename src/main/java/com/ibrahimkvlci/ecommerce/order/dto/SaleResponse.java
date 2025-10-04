package com.ibrahimkvlci.ecommerce.order.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for sale responses in the order module.
 * Contains the result of a sale transaction processing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public static class Order{

        private String orderId;
    }


}

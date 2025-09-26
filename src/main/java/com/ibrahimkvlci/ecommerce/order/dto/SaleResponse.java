package com.ibrahimkvlci.ecommerce.order.dto;

import java.util.Dictionary;

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

    public enum SaleStatusEnum {
        Error,
        Success,
        RedirectURL,
        RedirectHTML
    }

    private SaleStatusEnum saleStatusEnum;

    private String message;

    private String orderNumber;

    private String transactionId;

    private Dictionary<String, Object> privateResponse;
}

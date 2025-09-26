package com.ibrahimkvlci.ecommerce.payment.dto;

import java.util.Dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String message;

    private String orderNumber;

    private String transactionId;

    private Dictionary<String,Object> privateResponse;
}

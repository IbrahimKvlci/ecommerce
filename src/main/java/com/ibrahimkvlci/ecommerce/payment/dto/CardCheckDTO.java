package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardCheckDTO {

    public enum DeviceChannelEnum{
        Mobile,
        WebBrowser
    }

    private String cardNumber;

    private String cardHolderName;

    private String expirationDateYear;

    private String expirationDateYMonth;

    private String cvv;

    private DeviceChannelEnum deviceChannelEnum;
    
    private String clientIp;

    private String orderNumber;

    private double orderAmount;

    private BillAddressDetailDTO billAddressDetailDTO;
}

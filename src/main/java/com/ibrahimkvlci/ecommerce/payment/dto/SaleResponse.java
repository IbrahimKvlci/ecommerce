package com.ibrahimkvlci.ecommerce.payment.dto;


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

    @Data
    public static class Order{

        private String orderId;
    }

    public void setHostResponseCode(String hostResponseCode){
        this.hostResponseCode=hostResponseCode;
        switch (hostResponseCode) {
            case "00":
                this.saleStatusEnum=SaleStatusEnum.Success;
                break;
            default:
                this.saleStatusEnum=SaleStatusEnum.Error;
                break;
        }
    }
}

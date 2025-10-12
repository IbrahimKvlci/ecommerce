package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.payment.PaymentCheckoutSuccessEvent;
import com.ibrahimkvlci.ecommerce.order.application.CheckoutApp;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCheckoutAppBus {

    private final CheckoutApp checkoutApp;

    /**
     * Converts payment SaleResponse to order SaleResponse
     */
    private SaleResponse convertPaymentToOrderSaleResponse(com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse paymentResponse) {
        SaleResponse orderResponse = new SaleResponse();
        
        // Convert SaleStatusEnum
        if (paymentResponse.getSaleStatusEnum() != null) {
            switch (paymentResponse.getSaleStatusEnum()) {
                case Error:
                    orderResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
                    break;
                case Success:
                    orderResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
                    break;
                case RedirectURL:
                    orderResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectURL);
                    break;
                case RedirectHTML:
                    orderResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
                    break;
                default:
                    orderResponse.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
                    break;
            }
        }
        
        // Copy other fields
        orderResponse.setHostMessage(paymentResponse.getHostMessage());
        orderResponse.setHostResponseCode(paymentResponse.getHostResponseCode());
        orderResponse.setResponseMessage(paymentResponse.getResponseMessage());
        orderResponse.setHtmlResponse(paymentResponse.getHtmlResponse());
        
        // Convert Order object
        if (paymentResponse.getOrder() != null) {
            SaleResponse.Order orderOrder = new SaleResponse.Order();
            orderOrder.setOrderId(paymentResponse.getOrder().getOrderId());
            orderResponse.setOrder(orderOrder);
        }
        
        return orderResponse;
    }

    @EventListener
    public SaleResponse okCheckout(PaymentCheckoutSuccessEvent event) {
        com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse paymentSaleResponse = event.getSaleResponse();
        SaleResponse orderSaleResponse = convertPaymentToOrderSaleResponse(paymentSaleResponse);
        
        SaleResponse saleResponse = checkoutApp.okCheckout(orderSaleResponse);
        return saleResponse;
    }

    @EventListener
    public SaleResponse failCheckout(com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse paymentResponse) {
        SaleResponse orderSaleResponse = convertPaymentToOrderSaleResponse(paymentResponse);
        SaleResponse saleResponse = checkoutApp.failCheckout(orderSaleResponse);
        return saleResponse;
    }

}

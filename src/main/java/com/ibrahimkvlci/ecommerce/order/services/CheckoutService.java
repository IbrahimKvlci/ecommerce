package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;

public interface CheckoutService {

    DataResult<SaleResponse> checkoutPending(CheckoutRequestDTO request, String clientIp,
            RequestUtils.ClientType clientType);

    DataResult<SaleResponse> checkoutPending3D(CheckoutRequestDTO request, String clientIp,
            RequestUtils.ClientType clientType);

    DataResult<SaleResponse> okCheckout(SaleResponse response);

    DataResult<SaleResponse> failCheckout(SaleResponse response);

    DataResult<OrderDTO> completeCheckout(Long orderId);

}

package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.auth.UserAppBus;
import com.ibrahimkvlci.ecommerce.order.client.UserClient;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.ErrorDataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.SuccessDataResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderUserClientBus implements UserClient {

    private final UserAppBus userAppBus;

    @Override
    public DataResult<Long> getCustomerIdFromJWT() {
        var result = userAppBus.getUserIdFromJWT();
        DataResult<Long> dataResult;
        if (result.isSuccess()) {
            dataResult = new SuccessDataResult<>(result.getData());
        } else {
            dataResult = new ErrorDataResult<>(result.getMessage(), null);
        }
        return dataResult;
    }

}

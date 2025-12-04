package com.ibrahimkvlci.ecommerce.bus.address;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.address.client.UserClient;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.ErrorDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.bus.auth.UserAppBus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AddressUserClientBus implements UserClient {

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

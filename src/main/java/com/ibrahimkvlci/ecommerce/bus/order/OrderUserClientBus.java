package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.auth.UserAppBus;
import com.ibrahimkvlci.ecommerce.order.client.UserClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderUserClientBus implements UserClient {

    private final UserAppBus userAppBus;

    @Override
    public Long getCustomerIdFromJWT(){
        return userAppBus.getUserIdFromJWT();
    }

}

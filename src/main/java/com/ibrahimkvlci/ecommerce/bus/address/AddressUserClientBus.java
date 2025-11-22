package com.ibrahimkvlci.ecommerce.bus.address;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.address.client.UserClient;
import com.ibrahimkvlci.ecommerce.bus.auth.UserAppBus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AddressUserClientBus implements UserClient {

    private final UserAppBus userAppBus;

    @Override
    public Long getCustomerIdFromJWT() {
        return userAppBus.getUserIdFromJWT();
    }
}

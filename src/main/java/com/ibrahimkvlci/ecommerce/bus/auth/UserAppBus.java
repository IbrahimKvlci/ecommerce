package com.ibrahimkvlci.ecommerce.bus.auth;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.application.UserApp;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAppBus {

    private final UserApp userApp;
    
    public Long getUserIdFromJWT(){
        return userApp.getUserIdFromJWT();
    }
}

package com.ibrahimkvlci.ecommerce.auth.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.services.UserInfoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserApp {

    private final UserInfoService userInfoService;

    public Long getUserIdFromJWT(){
        return userInfoService.getUserIdFromJWT();
    }
}

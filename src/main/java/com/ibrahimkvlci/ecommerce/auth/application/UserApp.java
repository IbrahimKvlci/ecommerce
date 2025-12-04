package com.ibrahimkvlci.ecommerce.auth.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.services.UserInfoService;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.ErrorDataResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserApp {

    private final UserInfoService userInfoService;

    public DataResult<Long> getUserIdFromJWT() {
        try {
            return userInfoService.getUserIdFromJWT();
        } catch (Exception e) {
            return new ErrorDataResult<Long>(e.getMessage(), null);
        }
    }
}

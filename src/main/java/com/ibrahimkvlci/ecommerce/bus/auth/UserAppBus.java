package com.ibrahimkvlci.ecommerce.bus.auth;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.application.UserApp;
import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.ErrorDataResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAppBus {

    private final UserApp userApp;

    public DataResult<Long> getUserIdFromJWT() {
        try {
            return userApp.getUserIdFromJWT();
        } catch (AuthException e) {
            return new ErrorDataResult<Long>(e.getMessage(), null);
        }
    }
}

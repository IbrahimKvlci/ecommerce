package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;

public interface UserInfoService {

    Long getUserIdFromJWT() throws AuthException;
}

package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;

public interface UserInfoService {

    DataResult<Long> getUserIdFromJWT() throws AuthException;
}

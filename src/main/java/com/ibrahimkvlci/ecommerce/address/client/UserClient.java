package com.ibrahimkvlci.ecommerce.address.client;

import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;

public interface UserClient {

    DataResult<Long> getCustomerIdFromJWT();
}

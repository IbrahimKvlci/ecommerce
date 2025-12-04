package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;

public interface UserClient {

    DataResult<Long> getCustomerIdFromJWT();

}

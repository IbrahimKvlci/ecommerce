package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;

public interface CustomerClient {

    CustomerDTO getCustomerById(Long id);

    CustomerDTO getCustomerByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

}

package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;

public interface UserEventPublisher {

    void publishCustomerCreated(CustomerDTO customer);
}
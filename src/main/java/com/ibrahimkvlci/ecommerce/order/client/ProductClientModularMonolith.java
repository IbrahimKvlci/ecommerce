package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductClientModularMonolith implements ProductClient{


    private final OrderBus orderBus;

    @Override
    public ProductDTO getProductById(Long productId) {
        return orderBus.getProductById(productId);
    }

    @Override
    public boolean isProductAvailable(Long productId) {
        return orderBus.isProductAvailable(productId);
    }

}

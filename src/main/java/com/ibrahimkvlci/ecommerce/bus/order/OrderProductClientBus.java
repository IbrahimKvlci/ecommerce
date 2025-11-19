package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.ProductBus;
import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProductClientBus implements ProductClient {

    private final ProductBus productBus;

    @Override
    public ProductDTO getProductById(Long productId) {
        return new ProductDTO(productBus.getProductById(productId).getId(),
                productBus.getProductById(productId).getTitle(), productBus.getProductById(productId).getDescription());
    }

    @Override
    public boolean isProductAvailable(Long productId) {
        return productBus.isProductAvailable(productId);
    }

}

package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;

public interface ProductClient {

    ProductDTO getProductById(Long productId);

    boolean isProductAvailable(Long productId);
}

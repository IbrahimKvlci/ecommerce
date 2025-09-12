package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;

public interface CategoryClient {

    CategoryDTO getCategoryById(Long id);

    boolean existsById(Long id);
}

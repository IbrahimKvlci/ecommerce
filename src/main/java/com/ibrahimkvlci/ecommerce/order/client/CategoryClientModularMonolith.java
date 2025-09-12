package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryClientModularMonolith implements CategoryClient{


    private final OrderBus orderBus;
    
    @Override
    public CategoryDTO getCategoryById(Long id) {
        return orderBus.getCategoryById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orderBus.existsCategoryById(id);
    }

}

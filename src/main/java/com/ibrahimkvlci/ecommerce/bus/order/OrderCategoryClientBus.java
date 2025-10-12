package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.CategoryBus;
import com.ibrahimkvlci.ecommerce.order.client.CategoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCategoryClientBus implements CategoryClient {

    private final CategoryBus categoryBus;

    @Override
    public CategoryDTO getCategoryById(Long categoryId){
        return new CategoryDTO(categoryBus.getCategoryById(categoryId).getId(), categoryBus.getCategoryById(categoryId).getName());
    }

    @Override
    public boolean existsById(Long categoryId){
        return categoryBus.existsById(categoryId);
    }

}

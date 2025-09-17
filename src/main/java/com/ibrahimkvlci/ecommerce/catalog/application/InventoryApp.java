package com.ibrahimkvlci.ecommerce.catalog.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.InventoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryApp {

    private final InventoryService inventoryService;

    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId){
        return inventoryService.getInventoryByProductIdAndSellerId(productId, sellerId);
    }
}

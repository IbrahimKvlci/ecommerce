package com.ibrahimkvlci.ecommerce.catalog.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.id.InventoryId;
import com.ibrahimkvlci.ecommerce.catalog.services.InventoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryApp {

    private final InventoryService inventoryService;

    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return inventoryService.getInventoryByProductIdAndSellerId(productId, sellerId).getData();
    }

    public InventoryDTO updateInventory(Long sellerId, Long productId, int quantity, double price) {
        return inventoryService.updateInventory(new InventoryId(sellerId, productId), quantity, price).getData();
    }
}

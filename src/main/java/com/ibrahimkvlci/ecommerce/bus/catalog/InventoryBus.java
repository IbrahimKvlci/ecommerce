package com.ibrahimkvlci.ecommerce.bus.catalog;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.application.InventoryApp;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryBus {

    private final InventoryApp inventoryApp;

    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return inventoryApp.getInventoryByProductIdAndSellerId(productId, sellerId);
    }

    public InventoryDTO updateInventory(Long sellerId, Long productId, int quantity, double price) {
        return inventoryApp.updateInventory(sellerId, productId, quantity, price);
    }
}

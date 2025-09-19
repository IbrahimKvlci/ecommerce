package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.InventoryBus;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryClientModularMonolith implements InventoryClient {

    private final InventoryBus inventoryBus;

    @Override
    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        var inventory = inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId);
        return new InventoryDTO(inventory.getId(), inventory.getProductId(), inventory.getQuantity(), inventory.getSellerId(), inventory.getPrice());
    }

    @Override
    public InventoryDTO updateInventory(Long id, int quantity, double price) {
        var inventory = inventoryBus.updateInventory(id, quantity, price);
        return new InventoryDTO(inventory.getId(), inventory.getProductId(), inventory.getQuantity(), inventory.getSellerId(), inventory.getPrice());
    }

}

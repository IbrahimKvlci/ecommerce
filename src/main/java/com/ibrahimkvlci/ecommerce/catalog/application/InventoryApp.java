package com.ibrahimkvlci.ecommerce.catalog.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.services.InventoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryApp {

    private final InventoryService inventoryService;

    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId){
        return inventoryService.getInventoryByProductIdAndSellerId(productId, sellerId);
    }

    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO){
        Inventory inventory = inventoryService.mapToEntity(inventoryDTO);
        return inventoryService.updateInventory(id, inventory);
    }
}

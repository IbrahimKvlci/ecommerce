package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;

import java.util.List;

public interface InventoryService {

    InventoryDTO createInventory(Inventory inventory);
    InventoryDTO createInventory(InventoryDTO inventoryDTO);

    List<InventoryDTO> getAllInventories();

    InventoryDTO getInventoryById(Long id);

    List<InventoryDTO> getInventoriesByProductId(Long productId);

    InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId);

    InventoryDTO updateInventory(Long id, Inventory inventory);

    InventoryDTO updateInventory(Long id, int quantity, double price);

    void deleteInventory(Long id);
    
    /**
     * Convert DTO to entity
     */
    Inventory mapToEntity(InventoryDTO inventoryDTO);
    
    /**
     * Create DTO from entity
     */
    InventoryDTO mapToDTO(Inventory inventory);
}



package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;

import java.util.List;

public interface InventoryService {

    InventoryDTO createInventory(Inventory inventory);

    List<InventoryDTO> getAllInventories();

    InventoryDTO getInventoryById(Long id);

    List<InventoryDTO> getInventoriesByProductId(Long productId);

    InventoryDTO updateInventory(Long id, Inventory inventory);

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



package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    InventoryDTO createInventory(InventoryDTO inventoryDTO);

    List<InventoryDTO> getAllInventories();

    InventoryDTO getInventoryById(Long id);

    List<InventoryDTO> getInventoriesByProductId(Long productId);

    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);

    void deleteInventory(Long id);
}



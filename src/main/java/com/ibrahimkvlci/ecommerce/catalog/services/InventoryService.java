package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;

import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import java.util.List;

public interface InventoryService {

    DataResult<InventoryDTO> createInventory(Inventory inventory);

    DataResult<InventoryDTO> createInventory(InventoryDTO inventoryDTO);

    DataResult<List<InventoryDTO>> getAllInventories();

    DataResult<InventoryDTO> getInventoryById(Long id);

    DataResult<List<InventoryDTO>> getInventoriesByProductId(Long productId);

    DataResult<InventoryDTO> getInventoryByProductIdAndSellerId(Long productId, Long sellerId);

    DataResult<InventoryDisplayDTO> getInventoryDisplayByProductIdAndSellerId(Long productId, Long sellerId);

    DataResult<InventoryDTO> updateInventory(Long id, Inventory inventory);

    DataResult<InventoryDTO> updateInventory(Long id, int quantity, double price);

    Result deleteInventory(Long id);

    /**
     * Convert DTO to entity
     */
    Inventory mapToEntity(InventoryDTO inventoryDTO);

    /**
     * Create DTO from entity
     */
    InventoryDTO mapToDTO(Inventory inventory);
}

package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.id.InventoryId;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import java.util.List;

public interface InventoryService {

    DataResult<InventoryDTO> createInventory(InventoryRequestDTO inventoryRequestDTO);

    DataResult<List<InventoryDTO>> getAllInventories();

    DataResult<InventoryDTO> getInventoryById(InventoryId inventoryId);

    DataResult<List<InventoryDTO>> getInventoriesByProductId(Long productId);

    DataResult<InventoryDTO> getInventoryByProductIdAndSellerId(Long productId, Long sellerId);

    DataResult<InventoryDisplayDTO> getInventoryDisplayByProductIdAndSellerId(Long productId, Long sellerId);

    DataResult<InventoryDTO> updateInventory(InventoryId inventoryId, Inventory inventory);

    DataResult<InventoryDTO> updateInventory(InventoryId inventoryId, int quantity, double price);

    Result deleteInventory(InventoryId inventoryId);

    /**
     * Convert DTO to entity
     */
    Inventory mapToEntity(InventoryRequestDTO inventoryRequestDTO);

    /**
     * Convert DTO to entity
     */
    Inventory mapToEntity(InventoryDTO inventoryDTO);

    /**
     * Create DTO from entity
     */
    InventoryDTO mapToDTO(Inventory inventory);
}

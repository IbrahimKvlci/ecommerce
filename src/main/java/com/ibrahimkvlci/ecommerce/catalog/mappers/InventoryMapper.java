package com.ibrahimkvlci.ecommerce.catalog.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final ProductMapper productMapper;

    public Inventory toEntity(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setPrice(inventoryDTO.getPrice());
        inventory.setSellerId(inventoryDTO.getSellerId());
        inventory.setProduct(productMapper.toEntity(inventoryDTO.getProductDTO()));
        return inventory;
    }

    public InventoryDTO toDTO(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductDTO(productMapper.toDTO(inventory.getProduct()));
        dto.setQuantity(inventory.getQuantity());
        dto.setPrice(inventory.getPrice());
        dto.setSellerId(inventory.getSellerId());
        return dto;
    }
}



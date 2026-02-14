package com.ibrahimkvlci.ecommerce.catalog.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final ProductMapper productMapper;

    public Inventory toEntity(InventoryRequestDTO inventoryRequestDTO) {
        if (inventoryRequestDTO == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequestDTO.getQuantity());
        inventory.setPrice(inventoryRequestDTO.getPrice());
        inventory.setSellerId(inventoryRequestDTO.getSellerId());
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(inventoryRequestDTO.getProductId());
        inventory.setProduct(productMapper.toEntity(productDTO));
        return inventory;
    }

    public Inventory toEntity(InventoryDTO inventoryDTO) {
        if (inventoryDTO == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setPrice(inventoryDTO.getPrice());
        inventory.setSellerId(inventoryDTO.getSellerId());
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(inventoryDTO.getProduct().getId());
        inventory.setProduct(productMapper.toEntity(productDTO));
        return inventory;
    }

    public InventoryDTO toDTO(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryDTO dto = new InventoryDTO();
        dto.setProduct(productMapper.toDTO(inventory.getProduct()));
        dto.setQuantity(inventory.getQuantity());
        dto.setPrice(inventory.getPrice());
        dto.setSellerId(inventory.getSellerId());
        return dto;
    }
}

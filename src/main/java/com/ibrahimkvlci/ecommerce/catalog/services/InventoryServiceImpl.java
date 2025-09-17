package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.repositories.InventoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(Inventory inventory) {
        productRepository.findById(inventory.getProduct().getId())
                .orElseThrow(() -> new InventoryValidationException("Product not found with ID: " + inventory.getProduct().getId()));

        Inventory saved = inventoryRepository.save(inventory);
        return this.mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        return this.mapToDTO(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventoriesByProductId(Long productId) {
        List<Inventory> inventories = inventoryRepository.findAllByProductId(productId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No inventories found for product ID: " + productId);
        }
        return inventories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public InventoryDTO updateInventory(Long id, Inventory inventory) {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));

        if (!existing.getProduct().getId().equals(inventory.getProduct().getId())) {
            throw new InventoryValidationException("Product ID cannot be changed for an existing inventory");
        }

        existing.setQuantity(inventory.getQuantity());
        Inventory updated = inventoryRepository.save(existing);
        return this.mapToDTO(updated);
    }

    @Override
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new InventoryNotFoundException("Inventory not found with ID: " + id);
        }
        inventoryRepository.deleteById(id);
    }
    
    /**
     * Convert DTO to entity
     */
    public Inventory mapToEntity(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDTO.getId());
        inventory.setQuantity(inventoryDTO.getQuantity());
        // Note: Product needs to be set by the caller since it requires a Product entity
        return inventory;
    }
    
    /**
     * Create DTO from entity
     */
    public InventoryDTO mapToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setQuantity(inventory.getQuantity());
        return dto;
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return this.mapToDTO(inventoryRepository.findByProductIdAndSellerId(productId, sellerId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product ID: " + productId + " and seller ID: " + sellerId)));
    }

}



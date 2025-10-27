package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.repositories.InventoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;
import com.ibrahimkvlci.ecommerce.catalog.mappers.InventoryMapper;
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
    private final InventoryMapper inventoryMapper;

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
        List<Inventory> inventories=inventoryRepository.findAll();
        return inventories.stream().map(this::mapToDTO).collect(Collectors.toList());
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
        return inventoryMapper.toEntity(inventoryDTO);
    }
    
    /**
     * Create DTO from entity
     */
    public InventoryDTO mapToDTO(Inventory inventory) {
        return inventoryMapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return this.mapToDTO(inventoryRepository.findByProductIdAndSellerId(productId, sellerId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product ID: " + productId + " and seller ID: " + sellerId)));
    }

    @Override
    public InventoryDTO updateInventory(Long id, int quantity, double price) {
        InventoryDTO inventoryDTO = this.getInventoryById(id);
        inventoryDTO.setQuantity(quantity);
        inventoryDTO.setPrice(price);
        Inventory inventory = this.mapToEntity(inventoryDTO);
        return this.updateInventory(id, inventory);
    }

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = this.mapToEntity(inventoryDTO);
        return this.createInventory(inventory);
    }

}



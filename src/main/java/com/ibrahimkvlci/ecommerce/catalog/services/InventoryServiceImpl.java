package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
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
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new InventoryValidationException("Product not found with ID: " + inventoryDTO.getProductId()));

        Inventory saved = inventoryRepository.save(inventoryDTO.toEntity(product));
        return InventoryDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream().map(InventoryDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        return InventoryDTO.fromEntity(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getInventoriesByProductId(Long productId) {
        List<Inventory> inventories = inventoryRepository.findAllByProductId(productId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No inventories found for product ID: " + productId);
        }
        return inventories.stream().map(InventoryDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));

        if (!existing.getProduct().getId().equals(inventoryDTO.getProductId())) {
            throw new InventoryValidationException("Product ID cannot be changed for an existing inventory");
        }

        existing.setQuantity(inventoryDTO.getQuantity());
        Inventory updated = inventoryRepository.save(existing);
        return InventoryDTO.fromEntity(updated);
    }

    @Override
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new InventoryNotFoundException("Inventory not found with ID: " + id);
        }
        inventoryRepository.deleteById(id);
    }
}



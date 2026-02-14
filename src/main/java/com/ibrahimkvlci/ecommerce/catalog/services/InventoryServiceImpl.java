package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryRequestDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;
import com.ibrahimkvlci.ecommerce.catalog.models.id.InventoryId;
import com.ibrahimkvlci.ecommerce.catalog.repositories.InventoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;
import com.ibrahimkvlci.ecommerce.catalog.mappers.InventoryMapper;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;
    private final SearchService searchService;

    @Override
    public DataResult<InventoryDTO> createInventory(InventoryRequestDTO inventoryRequestDTO) {

        productRepository.findById(Objects.requireNonNull(inventoryRequestDTO.getProductId()))
                .orElseThrow(() -> new InventoryValidationException(
                        "Product not found with ID: " + inventoryRequestDTO.getProductId()));

        ProductDocument productDocument = searchService.getProductById(inventoryRequestDTO.getProductId()).getData();
        List<ProductDocument.Inventory> inventories = productDocument.getInventories();
        if (inventories == null) {
            inventories = new ArrayList<>();
        }
        boolean isInventoryExists = false;
        for (ProductDocument.Inventory inventory : inventories) {
            if (inventory.getSellerId().equals(inventoryRequestDTO.getSellerId())) {
                inventory.setStock(inventoryRequestDTO.getQuantity());
                inventory.setPrice(inventoryRequestDTO.getPrice());
                inventory.setDiscountPrice(inventoryRequestDTO.getDiscountPrice());
                isInventoryExists = true;
                break;
            }
        }
        if (!isInventoryExists) {
            inventories
                    .add(new ProductDocument.Inventory(inventoryRequestDTO.getSellerId(),
                            inventoryRequestDTO.getQuantity(),
                            inventoryRequestDTO.getPrice(), inventoryRequestDTO.getDiscountPrice()));
        }
        productDocument.setInventories(inventories);
        searchService.updateProduct(productDocument);

        Inventory saved = inventoryRepository.save(inventoryMapper.toEntity(inventoryRequestDTO));
        return new SuccessDataResult<>("Inventory created successfully", this.mapToDTO(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<InventoryDTO>> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return new SuccessDataResult<>("Inventories listed successfully",
                inventories.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<InventoryDTO> getInventoryById(InventoryId inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + inventoryId));
        return new SuccessDataResult<>("Inventory found successfully", this.mapToDTO(inventory));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<InventoryDTO>> getInventoriesByProductId(Long productId) {
        List<Inventory> inventories = inventoryRepository.findAllByProductId(productId);
        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No inventories found for product ID: " + productId);
        }
        return new SuccessDataResult<>("Inventories found successfully",
                inventories.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    public DataResult<InventoryDTO> updateInventory(InventoryId inventoryId, Inventory inventory) {
        Inventory existing = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + inventoryId));

        if (!existing.getProduct().getId().equals(inventory.getProduct().getId())) {
            throw new InventoryValidationException("Product ID cannot be changed for an existing inventory");
        }

        existing.setQuantity(inventory.getQuantity());
        Inventory updated = inventoryRepository.save(existing);
        return new SuccessDataResult<>("Inventory updated successfully", this.mapToDTO(updated));
    }

    @Override
    public Result deleteInventory(InventoryId inventoryId) {
        if (!inventoryRepository.existsById(Objects.requireNonNull(inventoryId))) {
            throw new InventoryNotFoundException("Inventory not found with ID: " + inventoryId);
        }
        inventoryRepository.deleteById(Objects.requireNonNull(inventoryId));
        return new SuccessResult("Inventory deleted successfully");
    }

    /**
     * Convert DTO to entity
     */
    public Inventory mapToEntity(InventoryRequestDTO inventoryRequestDTO) {
        return inventoryMapper.toEntity(inventoryRequestDTO);
    }

    /**
     * Create DTO from entity
     */
    public InventoryDTO mapToDTO(Inventory inventory) {
        return inventoryMapper.toDTO(inventory);
    }

    @Override
    public DataResult<InventoryDTO> getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return new SuccessDataResult<>("Inventory found successfully",
                this.mapToDTO(inventoryRepository.findByProductIdAndSellerId(productId, sellerId)
                        .orElseThrow(() -> new InventoryNotFoundException(
                                "Inventory not found for product ID: " + productId + " and seller ID: " + sellerId))));
    }

    @Override
    public DataResult<InventoryDTO> updateInventory(InventoryId inventoryId, int quantity, double price) {
        DataResult<InventoryDTO> inventoryResult = this.getInventoryById(inventoryId);
        if (!inventoryResult.isSuccess()) {
            return new com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult<>(
                    inventoryResult.getMessage(), null);
        }
        InventoryDTO inventoryDTO = inventoryResult.getData();
        inventoryDTO.setQuantity(quantity);
        inventoryDTO.setPrice(price);
        Inventory inventory = this.mapToEntity(inventoryDTO);
        return this.updateInventory(inventoryId, inventory);
    }

    @Override
    public DataResult<InventoryDisplayDTO> getInventoryDisplayByProductIdAndSellerId(Long productId, Long sellerId) {

        InventoryDTO inventoryDTO = this
                .mapToDTO(this.inventoryRepository.findByProductIdAndSellerId(productId, sellerId)
                        .orElseThrow(() -> new InventoryNotFoundException(
                                "Inventory not found for product ID: " + productId + " and seller ID: " + sellerId)));
        List<Inventory> otherInventories = this.inventoryRepository
                .findAllByProductIdAndSellerIdNot(productId, sellerId);

        List<InventoryDisplayDTO.OtherInventoryDTO> otherInventoriesDTO = otherInventories.stream()
                .map(this::mapToOtherInventoryDTO).collect(Collectors.toList());

        return new SuccessDataResult<>("Inventory display found successfully",
                new InventoryDisplayDTO(inventoryDTO, otherInventoriesDTO));
    }

    private InventoryDisplayDTO.OtherInventoryDTO mapToOtherInventoryDTO(Inventory inventory) {
        return new InventoryDisplayDTO.OtherInventoryDTO(inventory.getSellerId(),
                inventory.getPrice(), inventory.getQuantity());
    }

    @Override
    public Inventory mapToEntity(InventoryDTO inventoryDTO) {
        return inventoryMapper.toEntity(inventoryDTO);
    }

}

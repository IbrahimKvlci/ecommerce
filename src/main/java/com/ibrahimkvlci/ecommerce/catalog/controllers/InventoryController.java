package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.services.InventoryService;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/inventories")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<DataResult<InventoryDTO>> createInventory(@Valid @RequestBody InventoryDTO inventoryDTO) {
        Long productId = (inventoryDTO.getProductDTO() != null) ? inventoryDTO.getProductDTO().getId() : null;
        log.info("Creating inventory for product ID: {}", productId);
        DataResult<InventoryDTO> createdResult = inventoryService.createInventory(inventoryDTO);
        if (createdResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
        }
        return ResponseEntity.badRequest().body(
                new com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult<>(createdResult.getMessage(),
                        null));
    }

    @GetMapping
    public ResponseEntity<DataResult<List<InventoryDTO>>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<InventoryDTO>> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/display/{productId}/{sellerId}")
    public ResponseEntity<DataResult<InventoryDisplayDTO>> getInventoryDisplayByProductIdAndSellerId(
            @PathVariable Long productId, @PathVariable Long sellerId) {
        return ResponseEntity.ok(inventoryService.getInventoryDisplayByProductIdAndSellerId(productId, sellerId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<DataResult<List<InventoryDTO>>> getInventoriesByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoriesByProductId(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<InventoryDTO>> updateInventory(@PathVariable Long id,
            @Valid @RequestBody InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryService.mapToEntity(inventoryDTO);
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteInventory(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.deleteInventory(id));
    }
}

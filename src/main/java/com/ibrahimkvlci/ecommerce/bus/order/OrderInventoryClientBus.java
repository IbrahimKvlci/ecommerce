package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.InventoryBus;
import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderInventoryClientBus implements InventoryClient {

    private final InventoryBus inventoryBus;

    @Override
    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId){
        return new InventoryDTO(
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getId(),
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getProductId(),
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getQuantity(),
            sellerId,
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getPrice()
        );
    }

    @Override
    public InventoryDTO updateInventory(Long id, int quantity, double price){
        com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO inventory = inventoryBus.updateInventory(id, quantity, price);

        return new InventoryDTO(
            inventory.getId(),
            inventory.getProductId(),
            inventory.getQuantity(),
            inventory.getSellerId(),
            inventory.getPrice()
        );
    }

}

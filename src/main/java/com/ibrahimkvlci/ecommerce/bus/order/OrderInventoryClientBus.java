package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.InventoryBus;
import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderInventoryClientBus implements InventoryClient {

        private final InventoryBus inventoryBus;

        @Override
        public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
                var inventory = inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId);
                return new InventoryDTO(
                                new ProductDTO(inventory.getProduct().getId(),
                                                inventory.getProduct().getTitle(),
                                                inventory.getProduct().getDescription(),
                                                inventory.getProduct().getImagesUrl()),
                                inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getQuantity(),
                                sellerId,
                                inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getPrice());
        }

        @Override
        public InventoryDTO updateInventory(Long sellerId, Long productId, int quantity, double price) {
                com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO inventory = inventoryBus.updateInventory(sellerId,
                                productId,
                                quantity,
                                price);

                return new InventoryDTO(
                                new ProductDTO(inventory.getProduct().getId(),
                                                inventory.getProduct().getTitle(),
                                                inventory.getProduct().getDescription(),
                                                inventory.getProduct().getImagesUrl()),
                                inventory.getQuantity(),
                                inventory.getSellerId(),
                                inventory.getPrice());
        }

}

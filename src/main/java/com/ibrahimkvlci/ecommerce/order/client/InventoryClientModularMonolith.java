package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryClientModularMonolith implements InventoryClient {

    private final OrderBus orderBus;

    @Override
    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId) {
        return orderBus.getInventoryByProductIdAndSellerId(productId, sellerId);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        return orderBus.updateInventory(id, inventoryDTO);
    }

}

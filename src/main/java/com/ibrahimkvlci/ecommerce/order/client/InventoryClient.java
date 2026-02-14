package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;

public interface InventoryClient {

    InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId);

    InventoryDTO updateInventory(Long sellerId, Long productId, int quantity, double price);
}

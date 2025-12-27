package com.ibrahimkvlci.ecommerce.order.services;

import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.OrderItemDTO;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final InventoryClient inventoryClient;

    @Override
    public OrderItemDTO mapToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotalPrice(orderItem.getTotalPrice());
        dto.setProductInventory(
                inventoryClient.getInventoryByProductIdAndSellerId(orderItem.getProductId(), orderItem.getSellerId()));

        return dto;
    }

    @Override
    public OrderItem mapToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
        orderItem.setProductId(orderItemDTO.getProductInventory().getProduct().getId());
        orderItem.setSellerId(orderItemDTO.getProductInventory().getSellerId());

        return orderItem;
    }

}

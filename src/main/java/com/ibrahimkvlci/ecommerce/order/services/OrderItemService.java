package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.OrderItemDTO;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;

public interface OrderItemService {

    /**
     * Map order item to DTO
     */
    OrderItemDTO mapToDTO(OrderItem orderItem);

    /**
     * Map order item DTO to entity
     */
    OrderItem mapToEntity(OrderItemDTO orderItemDTO);
}

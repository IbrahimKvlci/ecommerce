package com.ibrahimkvlci.ecommerce.order.services;

import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.OrderItemDTO;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {


    private final ProductClient productClient;
    
    @Override
    public OrderItemDTO mapToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotalPrice(orderItem.getTotalPrice());
        dto.setProduct(productClient.getProductById(orderItem.getProductId()));
        
        return dto;
    }

    @Override
    public OrderItem mapToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
        orderItem.setProductId(orderItemDTO.getProduct().getId());
        
        return orderItem;
    }

}

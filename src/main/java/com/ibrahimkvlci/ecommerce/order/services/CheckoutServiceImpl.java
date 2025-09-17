package com.ibrahimkvlci.ecommerce.order.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CheckoutException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;
import com.ibrahimkvlci.ecommerce.order.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {


    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    @Override
    public OrderDTO checkoutPending(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            if(!productClient.isProductAvailable(cartItem.getProductId())){
                throw new CheckoutException(cartId, "Product with ID " + cartItem.getProductId() + " is not available");
            }
            ProductDTO product = productClient.getProductById(cartItem.getProductId());
            if(inventoryClient.getInventoryByProductIdAndSellerId(cartItem.getProductId(), cartItem.getSellerId()).getQuantity() < cartItem.getQuantity()){
                throw new CheckoutException(cartId, "Insufficient inventory for product ID " + cartItem.getProductId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItems.add(orderItem);
        }

        
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalPrice());
        order.setCustomerId(cart.getCustomerId());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return orderService.getOrderById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + order.getId()));
    }

    @Override
    public OrderDTO completeCheckout(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        for(OrderItem orderItem : order.getOrderItems()){
            InventoryDTO inventory = inventoryClient.getInventoryByProductIdAndSellerId(orderItem.getProductId(), orderItem.getSellerId());
            if(inventory.getQuantity() < orderItem.getQuantity()){
                throw new CheckoutException(orderId, "Insufficient inventory for product ID " + orderItem.getProductId(), true);
            }
        }
        for(OrderItem orderItem : order.getOrderItems()){
            InventoryDTO inventory = inventoryClient.getInventoryByProductIdAndSellerId(orderItem.getProductId(), orderItem.getSellerId());
            inventoryClient.updateInventory(inventory.getId(), new InventoryDTO(inventory.getId(), inventory.getProductId(), inventory.getQuantity() - orderItem.getQuantity(), inventory.getSellerId()));
        }
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return orderService.getOrderById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + order.getId()));
    }

}

package com.ibrahimkvlci.ecommerce.order.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderNotFoundException;
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

    @Override
    public OrderDTO checkout(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            if(!productClient.isProductAvailable(cartItem.getProductId())){
                return null;
            }
            ProductDTO product = productClient.getProductById(cartItem.getProductId());
            if(product.getQuantity() < cartItem.getQuantity()){
                return null;
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

}

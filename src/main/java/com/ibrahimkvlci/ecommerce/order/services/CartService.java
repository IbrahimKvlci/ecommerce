package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.models.Cart;

import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

import java.util.List;

public interface CartService {

    /**
     * Create a new cart
     */
    DataResult<CartDTO> createCart(CreateCartRequest request);

    /**
     * Get all carts
     */
    DataResult<List<CartDTO>> getAllCarts();

    /**
     * Get cart by ID
     */
    DataResult<CartDTO> getCartById(Long id);

    /**
     * Get cart by customer ID
     */
    DataResult<CartDTO> getCartOfCustomer();

    /**
     * Delete a cart
     */
    Result deleteCart(Long id);

    /**
     * Calculate cart total price
     */
    DataResult<Double> calculateCartTotal(Long cartId);

    /**
     * Clear cart items
     */
    DataResult<CartDTO> clearCart(Long cartId);

    /**
     * Get cart with items
     */
    DataResult<CartDTO> getCartWithItems(Long cartId);

    /**
     * Get cart with items by customer ID
     */
    DataResult<CartDTO> getCartWithItemsByCustomerId(Long customerId);

    /**
     * Map cart to DTO
     */
    CartDTO mapToDTO(Cart cart);

    /**
     * Map cart DTO to entity
     */
    Cart mapToEntity(CartDTO cartDTO);
}

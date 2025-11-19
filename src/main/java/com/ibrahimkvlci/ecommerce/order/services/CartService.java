package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.models.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {

    /**
     * Create a new cart
     */
    CartDTO createCart(CreateCartRequest request);

    /**
     * Get all carts
     */
    List<CartDTO> getAllCarts();

    /**
     * Get cart by ID
     */
    Optional<CartDTO> getCartById(Long id);

    /**
     * Get cart by customer ID
     */
    Optional<CartDTO> getCartByCustomerId(Long customerId);

    /**
     * Delete a cart
     */
    void deleteCart(Long id);

    /**
     * Calculate cart total price
     */
    Double calculateCartTotal(Long cartId);

    /**
     * Clear cart items
     */
    CartDTO clearCart(Long cartId);

    /**
     * Get cart with items
     */
    Optional<CartDTO> getCartWithItems(Long cartId);

    /**
     * Get cart with items by customer ID
     */
    Optional<CartDTO> getCartWithItemsByCustomerId(Long customerId);

    /**
     * Map cart to DTO
     */
    CartDTO mapToDTO(Cart cart);

    /**
     * Map cart DTO to entity
     */
    Cart mapToEntity(CartDTO cartDTO);
}

package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    
    /**
     * Add item to cart
     */
    CartItemDTO addCartItem(AddCartItemRequest request);
    
    /**
     * Get all cart items
     */
    List<CartItemDTO> getAllCartItems();
    
    /**
     * Get cart item by ID
     */
    Optional<CartItemDTO> getCartItemById(Long id);
    
    /**
     * Get cart items by cart ID
     */
    List<CartItemDTO> getCartItemsByCartId(Long cartId);
    
    /**
     * Get cart item by cart ID and product ID
     */
    Optional<CartItemDTO> getCartItemByCartIdAndProductId(Long cartId, Long productId);
    
    /**
     * Update cart item
     */
    CartItemDTO updateCartItem(Long id, UpdateCartItemRequest request);
    
    /**
     * Delete cart item
     */
    void deleteCartItem(Long id);
    
    /**
     * Delete cart item by cart ID and product ID
     */
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);
    
    /**
     * Delete all cart items by cart ID
     */
    void deleteCartItemsByCartId(Long cartId);
    
    /**
     * Check if cart item exists for cart and product
     */
    boolean cartItemExistsForCartAndProduct(Long cartId, Long productId);
    
    /**
     * Get total quantity of items in cart
     */
    Long getTotalQuantityByCartId(Long cartId);
    
    /**
     * Get total price of cart items by cart
     */
    Double getTotalPriceByCartId(Long cartId);
    
    /**
     * Map cart item to DTO
     */
    CartItemDTO mapToDTO(CartItem cartItem);
    
    /**
     * Map cart item DTO to entity
     */
    CartItem mapToEntity(CartItemDTO cartItemDTO);
}

package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;

import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

import java.util.List;

public interface CartItemService {

    /**
     * Add item to cart
     */
    DataResult<CartItemDTO> addCartItem(AddCartItemRequest request);

    /**
     * Get all cart items
     */
    DataResult<List<CartItemDTO>> getAllCartItems();

    /**
     * Get cart item by ID
     */
    DataResult<CartItemDTO> getCartItemById(Long id);

    /**
     * Get cart items by cart ID
     */
    DataResult<List<CartItemDTO>> getCartItemsByCartId(Long cartId);

    /**
     * Get cart item by cart ID and product ID
     */
    DataResult<CartItemDTO> getCartItemByCartIdAndProductIdAndSellerId(Long cartId, Long productId,
            Long sellerId);

    /**
     * Update cart item
     */
    DataResult<CartItemDTO> updateCartItem(Long id, UpdateCartItemRequest request);

    /**
     * Delete cart item
     */
    Result deleteCartItem(Long id);

    /**
     * Delete cart item by cart ID and product ID
     */
    Result deleteCartItemByCartIdAndProductId(Long cartId, Long productId);

    /**
     * Delete all cart items by cart ID
     */
    Result deleteCartItemsByCartId(Long cartId);

    /**
     * Check if cart item exists for cart and product
     */
    DataResult<Boolean> cartItemExistsForCartAndProduct(Long cartId, Long productId);

    /**
     * Get total quantity of items in cart
     */
    DataResult<Long> getTotalQuantityByCartId(Long cartId);

    /**
     * Map cart item to DTO
     */
    CartItemDTO mapToDTO(CartItem cartItem);

    /**
     * Map cart item DTO to entity
     */
    CartItem mapToEntity(CartItemDTO cartItemDTO);
}

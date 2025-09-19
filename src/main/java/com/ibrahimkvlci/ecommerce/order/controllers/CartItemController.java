package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.services.CartItemService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CartItem operations.
 * Provides endpoints for CRUD operations and cart item management.
 */
@RestController
@RequestMapping("/api/orders/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {
    
    private final CartItemService cartItemService;
    
    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    
    /**
     * Add item to cart
     */
    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItem(@Valid @RequestBody AddCartItemRequest request) {
        CartItemDTO cartItem = cartItemService.addCartItem(request);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    
    /**
     * Get all cart items
     */
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        List<CartItemDTO> cartItems = cartItemService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }
    
    /**
     * Get cart item by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
        return cartItemService.getCartItemById(id)
                .map(cartItem -> ResponseEntity.ok(cartItem))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get cart items by cart ID
     */
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemDTO>> getCartItemsByCartId(@PathVariable Long cartId) {
        List<CartItemDTO> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }
    
    /**
     * Get cart item by cart ID and product ID
     */
    @GetMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<CartItemDTO> getCartItemByCartIdAndProductId(
            @PathVariable Long cartId, 
            @PathVariable Long productId,
            @PathVariable Long sellerId) {
        return cartItemService.getCartItemByCartIdAndProductIdAndSellerId(cartId, productId, sellerId)
                .map(cartItem -> ResponseEntity.ok(cartItem))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update cart item
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, 
                                                     @Valid @RequestBody UpdateCartItemRequest request) {
        try {
            CartItemDTO cartItem = cartItemService.updateCartItem(id, request);
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete cart item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        try {
            cartItemService.deleteCartItem(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete cart item by cart ID and product ID
     */
    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<Void> deleteCartItemByCartIdAndProductId(
            @PathVariable Long cartId, 
            @PathVariable Long productId) {
        try {
            cartItemService.deleteCartItemByCartIdAndProductId(cartId, productId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete all cart items by cart ID
     */
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Void> deleteCartItemsByCartId(@PathVariable Long cartId) {
        cartItemService.deleteCartItemsByCartId(cartId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if cart item exists for cart and product
     */
    @GetMapping("/cart/{cartId}/product/{productId}/exists")
    public ResponseEntity<Boolean> cartItemExistsForCartAndProduct(
            @PathVariable Long cartId, 
            @PathVariable Long productId) {
        boolean exists = cartItemService.cartItemExistsForCartAndProduct(cartId, productId);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * Get total quantity of items in cart
     */
    @GetMapping("/cart/{cartId}/total-quantity")
    public ResponseEntity<Long> getTotalQuantityByCartId(@PathVariable Long cartId) {
        Long totalQuantity = cartItemService.getTotalQuantityByCartId(cartId);
        return ResponseEntity.ok(totalQuantity);
    }
}

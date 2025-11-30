package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.services.CartItemService;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

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
    public ResponseEntity<DataResult<CartItemDTO>> addCartItem(@Valid @RequestBody AddCartItemRequest request) {
        return new ResponseEntity<>(cartItemService.addCartItem(request), HttpStatus.CREATED);
    }

    /**
     * Get all cart items
     */
    @GetMapping
    public ResponseEntity<DataResult<List<CartItemDTO>>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }

    /**
     * Get cart item by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CartItemDTO>> getCartItemById(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    /**
     * Get cart items by cart ID
     */
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<DataResult<List<CartItemDTO>>> getCartItemsByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByCartId(cartId));
    }

    /**
     * Get cart item by cart ID and product ID
     */
    @GetMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<DataResult<CartItemDTO>> getCartItemByCartIdAndProductId(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @PathVariable Long sellerId) {
        return ResponseEntity
                .ok(cartItemService.getCartItemByCartIdAndProductIdAndSellerId(cartId, productId, sellerId));
    }

    /**
     * Update cart item
     */
    @PutMapping("/{id}")
    public ResponseEntity<DataResult<CartItemDTO>> updateCartItem(@PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(cartItemService.updateCartItem(id, request));
    }

    /**
     * Delete cart item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCartItem(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.deleteCartItem(id));
    }

    /**
     * Delete cart item by cart ID and product ID
     */
    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<Result> deleteCartItemByCartIdAndProductId(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartItemService.deleteCartItemByCartIdAndProductId(cartId, productId));
    }

    /**
     * Delete all cart items by cart ID
     */
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Result> deleteCartItemsByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.deleteCartItemsByCartId(cartId));
    }

    /**
     * Check if cart item exists for cart and product
     */
    @GetMapping("/cart/{cartId}/product/{productId}/exists")
    public ResponseEntity<DataResult<Boolean>> cartItemExistsForCartAndProduct(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(cartItemService.cartItemExistsForCartAndProduct(cartId, productId));
    }

    /**
     * Get total quantity of items in cart
     */
    @GetMapping("/cart/{cartId}/total-quantity")
    public ResponseEntity<DataResult<Long>> getTotalQuantityByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getTotalQuantityByCartId(cartId));
    }
}

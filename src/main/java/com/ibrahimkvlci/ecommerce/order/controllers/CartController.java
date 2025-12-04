package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.services.CartService;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Cart operations.
 * Provides endpoints for CRUD operations and cart management.
 */
@RestController
@RequestMapping("/api/orders/carts")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Create a new cart
     */
    @PostMapping
    public ResponseEntity<DataResult<CartDTO>> createCart(@Valid @RequestBody CreateCartRequest request) {
        return new ResponseEntity<>(cartService.createCart(request), HttpStatus.CREATED);
    }

    /**
     * Get all carts
     */
    @GetMapping
    public ResponseEntity<DataResult<List<CartDTO>>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    /**
     * Get cart by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CartDTO>> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    /**
     * Get cart by customer ID
     */
    @GetMapping("/customer")
    public ResponseEntity<DataResult<CartDTO>> getCartOfCustomer() {
        return ResponseEntity.ok(cartService.getCartOfCustomer());
    }

    /**
     * Get cart with items by ID
     */
    @GetMapping("/{id}/with-items")
    public ResponseEntity<DataResult<CartDTO>> getCartWithItems(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartWithItems(id));
    }

    /**
     * Get cart with items by customer ID
     */
    @GetMapping("/customer/{customerId}/with-items")
    public ResponseEntity<DataResult<CartDTO>> getCartWithItemsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartWithItemsByCustomerId(customerId));
    }

    /**
     * Delete a cart
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCart(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.deleteCart(id));
    }

    /**
     * Clear cart items
     */
    @DeleteMapping("/{id}/items")
    public ResponseEntity<DataResult<CartDTO>> clearCart(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.clearCart(id));
    }

    /**
     * Calculate cart total price
     */
    @GetMapping("/{id}/total")
    public ResponseEntity<DataResult<Double>> calculateCartTotal(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.calculateCartTotal(id));
    }
}

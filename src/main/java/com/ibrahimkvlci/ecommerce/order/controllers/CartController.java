package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.services.CartService;

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
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CreateCartRequest request) {
        CartDTO cart = cartService.createCart(request);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
    
    /**
     * Get all carts
     */
    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
    
    /**
     * Get cart by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        return cartService.getCartById(id)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get cart by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartDTO> getCartByCustomerId(@PathVariable Long customerId) {
        return cartService.getCartByCustomerId(customerId)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get cart with items by ID
     */
    @GetMapping("/{id}/with-items")
    public ResponseEntity<CartDTO> getCartWithItems(@PathVariable Long id) {
        return cartService.getCartWithItems(id)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get cart with items by customer ID
     */
    @GetMapping("/customer/{customerId}/with-items")
    public ResponseEntity<CartDTO> getCartWithItemsByCustomerId(@PathVariable Long customerId) {
        return cartService.getCartWithItemsByCustomerId(customerId)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Delete a cart
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteCart(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Clear cart items
     */
    @DeleteMapping("/{id}/items")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long id) {
        try {
            CartDTO cart = cartService.clearCart(id);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Calculate cart total price
     */
    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calculateCartTotal(@PathVariable Long id) {
        try {
            Double total = cartService.calculateCartTotal(id);
            return ResponseEntity.ok(total);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

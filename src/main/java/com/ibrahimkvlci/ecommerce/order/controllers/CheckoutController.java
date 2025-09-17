package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CheckoutResponseDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.services.CheckoutService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Checkout operations.
 * Provides endpoints for cart checkout and order completion.
 */
@RestController
@RequestMapping("/api/orders/checkout")
@CrossOrigin(origins = "*")
public class CheckoutController {
    
    private final CheckoutService checkoutService;
    
    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    
    /**
     * Initiate checkout process from a cart
     * Creates a pending order from cart items
     */
    @PostMapping("/initiate")
    public ResponseEntity<CheckoutResponseDTO> initiateCheckout(@Valid @RequestBody CheckoutRequestDTO request) {
        OrderDTO order = checkoutService.checkoutPending(request.getCartId());
        
        CheckoutResponseDTO response = new CheckoutResponseDTO(
            order, 
            "Checkout initiated successfully. Order is pending confirmation."
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Complete checkout process
     * Confirms the order and updates inventory
     */
    @PostMapping("/complete/{orderId}")
    public ResponseEntity<CheckoutResponseDTO> completeCheckout(@PathVariable Long orderId) {
        OrderDTO order = checkoutService.completeCheckout(orderId);
        
        CheckoutResponseDTO response = new CheckoutResponseDTO(
            order, 
            "Checkout completed successfully. Order confirmed."
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get checkout status for an order
     */
    @GetMapping("/status/{orderId}")
    public ResponseEntity<CheckoutResponseDTO> getCheckoutStatus(@PathVariable Long orderId) {
        // This would typically involve checking the order status
        // For now, we'll return a simple status check
        return ResponseEntity.ok(new CheckoutResponseDTO(
            orderId, null, null, null, null, 
            null, null, 
            "Checkout status retrieved successfully"
        ));
    }
}

package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CheckoutResponseDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.services.CheckoutService;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<DataResult<SaleResponse>> initiateCheckout(@Valid @RequestBody CheckoutRequestDTO request,
            HttpServletRequest httpRequest) {
        String clientIp = RequestUtils.getClientIp(httpRequest);
        RequestUtils.ClientType clientType = RequestUtils.getClientType(httpRequest);

        return ResponseEntity.ok(checkoutService.checkoutPending(request, clientIp, clientType));
    }

    @PostMapping("/initiate/3d")
    public ResponseEntity<DataResult<SaleResponse>> initiateCheckout3D(@Valid @RequestBody CheckoutRequestDTO request,
            HttpServletRequest httpRequest) {
        String clientIp = RequestUtils.getClientIp(httpRequest);
        RequestUtils.ClientType clientType = RequestUtils.getClientType(httpRequest);

        return ResponseEntity.ok(checkoutService.checkoutPending3D(request, clientIp, clientType));
    }

    /**
     * Complete checkout process
     * Confirms the order and updates inventory
     */
    @PostMapping("/complete/{orderId}")
    public ResponseEntity<DataResult<OrderDTO>> completeCheckout(@PathVariable Long orderId) {
        return ResponseEntity.ok(checkoutService.completeCheckout(orderId));
    }

    /**
     * Get checkout status for an order
     */
    @GetMapping("/status/{orderId}")
    public ResponseEntity<DataResult<CheckoutResponseDTO>> getCheckoutStatus(@PathVariable Long orderId) {
        // This would typically involve checking the order status
        // For now, we'll return a simple status check
        // TODO: Implement proper status check returning DataResult
        return null; // Placeholder as service doesn't have this method yet or it needs to be
                     // implemented
    }
}

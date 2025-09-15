package com.ibrahimkvlci.ecommerce.payment.controllers;

import com.ibrahimkvlci.ecommerce.payment.dto.PaymentMethodDTO;
import com.ibrahimkvlci.ecommerce.payment.models.PaymentMethod;
import com.ibrahimkvlci.ecommerce.payment.services.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for PaymentMethod operations.
 * Provides endpoints for CRUD operations and payment method search.
 */
@RestController
@RequestMapping("/api/payment/methods")
@CrossOrigin(origins = "*")
public class PaymentMethodController {
    
    private final PaymentMethodService paymentMethodService;
    
    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }
    
    /**
     * Create a new payment method
     */
    @PostMapping
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodService.createPaymentMethod(paymentMethodService.mapToEntity(paymentMethodDTO));
        PaymentMethodDTO createdDTO = paymentMethodService.mapToDTO(paymentMethod);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }
    
    /**
     * Get all payment methods
     */
    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethods.stream()
                .map(paymentMethodService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentMethodDTOs);
    }
    
    /**
     * Get payment method by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodById(@PathVariable Long id) {
        return paymentMethodService.getPaymentMethodById(id)
                .map(paymentMethod -> ResponseEntity.ok(paymentMethodService.mapToDTO(paymentMethod)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing payment method
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@PathVariable Long id, 
                                                             @Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodService.mapToEntity(paymentMethodDTO));
        PaymentMethodDTO updatedDTO = paymentMethodService.mapToDTO(updatedPaymentMethod);
        return ResponseEntity.ok(updatedDTO);
    }
    
    /**
     * Delete a payment method
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get payment method by name
     */
    @GetMapping("/search/name")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodByName(@RequestParam String name) {
        return paymentMethodService.getPaymentMethodByName(name)
                .map(paymentMethod -> ResponseEntity.ok(paymentMethodService.mapToDTO(paymentMethod)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Search payment methods by name containing keyword
     */
    @GetMapping("/search/name-contains")
    public ResponseEntity<List<PaymentMethodDTO>> searchPaymentMethodsByName(@RequestParam String name) {
        List<PaymentMethod> paymentMethods = paymentMethodService.searchPaymentMethodsByName(name);
        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethods.stream()
                .map(paymentMethodService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentMethodDTOs);
    }
    
    /**
     * Check if payment method exists by name
     */
    @GetMapping("/exists/name")
    public ResponseEntity<Boolean> checkPaymentMethodExistsByName(@RequestParam String name) {
        boolean exists = paymentMethodService.paymentMethodExistsByName(name);
        return ResponseEntity.ok(exists);
    }
}

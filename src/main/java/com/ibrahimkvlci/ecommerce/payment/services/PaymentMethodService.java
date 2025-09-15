package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentMethod;
import com.ibrahimkvlci.ecommerce.payment.dto.PaymentMethodDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
    
    /**
     * Create a new payment method
     */
    PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);
    
    /**
     * Get all payment methods
     */
    List<PaymentMethod> getAllPaymentMethods();
    
    /**
     * Get payment method by ID
     */
    Optional<PaymentMethod> getPaymentMethodById(Long id);
    
    /**
     * Update an existing payment method
     */
    PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod);
    
    /**
     * Delete a payment method by ID
     */
    void deletePaymentMethod(Long id);
    
    /**
     * Find payment method by name
     */
    Optional<PaymentMethod> getPaymentMethodByName(String name);
    
    /**
     * Search payment methods by name containing keyword
     */
    List<PaymentMethod> searchPaymentMethodsByName(String name);
    
    /**
     * Check if payment method exists by name
     */
    boolean paymentMethodExistsByName(String name);
    
    /**
     * Convert DTO to entity
     */
    PaymentMethod mapToEntity(PaymentMethodDTO paymentMethodDTO);
    
    /**
     * Create DTO from entity
     */
    PaymentMethodDTO mapToDTO(PaymentMethod paymentMethod);
}

package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentDetail;
import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDetailDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentDetailService {
    
    /**
     * Create a new payment detail
     */
    PaymentDetail createPaymentDetail(PaymentDetail paymentDetail);
    
    /**
     * Get all payment details
     */
    List<PaymentDetail> getAllPaymentDetails();
    
    /**
     * Get payment detail by ID
     */
    Optional<PaymentDetail> getPaymentDetailById(Long id);
    
    /**
     * Update an existing payment detail
     */
    PaymentDetail updatePaymentDetail(Long id, PaymentDetail paymentDetail);
    
    /**
     * Delete a payment detail by ID
     */
    void deletePaymentDetail(Long id);
    
    /**
     * Get payment details by order ID
     */
    List<PaymentDetail> getPaymentDetailsByOrderId(Long orderId);
    
    /**
     * Get payment details by customer ID
     */
    List<PaymentDetail> getPaymentDetailsByCustomerId(Long customerId);
    
    /**
     * Get payment details by payment status
     */
    List<PaymentDetail> getPaymentDetailsByStatus(boolean isPaid);
    
    /**
     * Convert DTO to entity
     */
    PaymentDetail mapToEntity(PaymentDetailDTO paymentDetailDTO);
    
    /**
     * Create DTO from entity
     */
    PaymentDetailDTO mapToDTO(PaymentDetail paymentDetail);
}

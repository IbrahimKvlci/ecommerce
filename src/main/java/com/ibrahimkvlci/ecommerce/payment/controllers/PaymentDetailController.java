package com.ibrahimkvlci.ecommerce.payment.controllers;

import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDetailDTO;
import com.ibrahimkvlci.ecommerce.payment.models.PaymentDetail;
import com.ibrahimkvlci.ecommerce.payment.services.PaymentDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for PaymentDetail operations.
 * Provides endpoints for CRUD operations and payment detail search.
 */
@RestController
@RequestMapping("/api/payment/details")
@CrossOrigin(origins = "*")
public class PaymentDetailController {
    
    private final PaymentDetailService paymentDetailService;
    
    @Autowired
    public PaymentDetailController(PaymentDetailService paymentDetailService) {
        this.paymentDetailService = paymentDetailService;
    }
    
    /**
     * Get all payment details
     */
    @GetMapping
    public ResponseEntity<List<PaymentDetailDTO>> getAllPaymentDetails() {
        List<PaymentDetail> paymentDetails = paymentDetailService.getAllPaymentDetails();
        List<PaymentDetailDTO> paymentDetailDTOs = paymentDetails.stream()
                .map(paymentDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDetailDTOs);
    }
    
    /**
     * Get payment detail by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailDTO> getPaymentDetailById(@PathVariable Long id) {
        return paymentDetailService.getPaymentDetailById(id)
                .map(paymentDetail -> ResponseEntity.ok(paymentDetailService.mapToDTO(paymentDetail)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing payment detail
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDetailDTO> updatePaymentDetail(@PathVariable Long id, 
                                                             @Valid @RequestBody PaymentDetailDTO paymentDetailDTO) {
        PaymentDetail updatedPaymentDetail = paymentDetailService.updatePaymentDetail(id, paymentDetailService.mapToEntity(paymentDetailDTO));
        PaymentDetailDTO updatedDTO = paymentDetailService.mapToDTO(updatedPaymentDetail);
        return ResponseEntity.ok(updatedDTO);
    }
    
    /**
     * Delete a payment detail
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDetail(@PathVariable Long id) {
        paymentDetailService.deletePaymentDetail(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get payment details by order ID
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDetailDTO>> getPaymentDetailsByOrderId(@PathVariable Long orderId) {
        List<PaymentDetail> paymentDetails = paymentDetailService.getPaymentDetailsByOrderId(orderId);
        List<PaymentDetailDTO> paymentDetailDTOs = paymentDetails.stream()
                .map(paymentDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDetailDTOs);
    }
    
    /**
     * Get payment details by payment status
     */
    @GetMapping("/status/{isPaid}")
    public ResponseEntity<List<PaymentDetailDTO>> getPaymentDetailsByStatus(@PathVariable boolean isPaid) {
        List<PaymentDetail> paymentDetails = paymentDetailService.getPaymentDetailsByStatus(isPaid);
        List<PaymentDetailDTO> paymentDetailDTOs = paymentDetails.stream()
                .map(paymentDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentDetailDTOs);
    }
    
    
    
   
    
    
    
}

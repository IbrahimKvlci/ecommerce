package com.ibrahimkvlci.ecommerce.payment.repositories;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    
    /**
     * Find payment details by order ID
     */
    List<PaymentDetail> findByOrderId(Long orderId);
    
    /**
     * Find payment details by customer ID
     */
    List<PaymentDetail> findByCustomerId(Long customerId);
    
    /**
     * Find payment details by payment status
     */
    List<PaymentDetail> findByIsPaid(boolean isPaid);
}

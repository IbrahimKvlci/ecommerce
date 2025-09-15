package com.ibrahimkvlci.ecommerce.payment.repositories;

import com.ibrahimkvlci.ecommerce.payment.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    
    /**
     * Find payment method by name (case-insensitive)
     */
    PaymentMethod findByNameIgnoreCase(String name);
    
    /**
     * Check if payment method exists by name
     */
    boolean existsByNameIgnoreCase(String name);
    
    /**
     * Find payment methods by name containing the given keyword (case-insensitive)
     */
    List<PaymentMethod> findByNameContainingIgnoreCase(String name);
}

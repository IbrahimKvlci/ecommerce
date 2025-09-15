package com.ibrahimkvlci.ecommerce.order.repositories;

import com.ibrahimkvlci.ecommerce.order.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    /**
     * Find cart by customer ID
     */
    Optional<Cart> findByCustomerId(Long customerId);
    
    /**
     * Check if cart exists for customer
     */
    boolean existsByCustomerId(Long customerId);
}

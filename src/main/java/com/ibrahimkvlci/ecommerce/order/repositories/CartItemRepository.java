package com.ibrahimkvlci.ecommerce.order.repositories;

import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Find cart items by cart ID
     */
    List<CartItem> findByCartId(Long cartId);
    
    /**
     * Find cart item by cart ID and product ID
     */
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    
    /**
     * Find cart items by product ID
     */
    List<CartItem> findByProductId(Long productId);
    
    /**
     * Find cart items by cart ordered by creation date descending
     */
    List<CartItem> findByCartIdOrderByCreatedAtDesc(Long cartId);
    
    /**
     * Find recent cart items (last N days)
     */
    @Query("SELECT ci FROM CartItem ci WHERE ci.createdAt >= :since ORDER BY ci.createdAt DESC")
    List<CartItem> findRecentCartItems(@Param("since") LocalDateTime since);
    
    /**
     * Get total count of cart items by cart
     */
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Long countByCartId(@Param("cartId") Long cartId);
    
    /**
     * Get total quantity of items in cart
     */
    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Long getTotalQuantityByCartId(@Param("cartId") Long cartId);
    
    /**
     * Get total price of cart items by cart
     */
    @Query("SELECT SUM(ci.totalPrice) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Double getTotalPriceByCartId(@Param("cartId") Long cartId);
    
    /**
     * Delete cart items by cart ID
     */
    void deleteByCartId(Long cartId);
    
    /**
     * Delete cart item by cart ID and product ID
     */
    void deleteByCartIdAndProductId(Long cartId, Long productId);

    /**
     * Check if cart item exists by cart ID and product ID
     */
    boolean existsByCartIdAndProductId(Long cartId, Long productId);
}

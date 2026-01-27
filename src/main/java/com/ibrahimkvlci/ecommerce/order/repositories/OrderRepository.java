package com.ibrahimkvlci.ecommerce.order.repositories;

import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find orders by customer ID
     */
    List<Order> findByCustomerId(Long customerId);

    /**
     * Find orders by status
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Find orders by order number
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Check if order exists by order number
     */
    boolean existsByOrderNumber(String orderNumber);

    /**
     * Find orders created between two dates
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find orders by customer and status
     */
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    /**
     * Find orders with total amount greater than or equal to given amount
     */
    List<Order> findByTotalAmountGreaterThanEqual(Double minAmount);

    /**
     * Find orders with total amount less than or equal to given amount
     */
    List<Order> findByTotalAmountLessThanEqual(Double maxAmount);

    /**
     * Find orders by total amount range
     */
    List<Order> findByTotalAmountBetween(Double minAmount, Double maxAmount);

    /**
     * Get total count of orders by status
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(@Param("status") OrderStatus status);

    /**
     * Get total revenue from orders by status
     */
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status")
    Double getTotalRevenueByStatus(@Param("status") OrderStatus status);

    /**
     * Find orders by customer ordered by creation date descending
     */
    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    Page<Order> findByCustomerIdAndStatusNot(Long customerId, OrderStatus status, Pageable pageable);

    /**
     * Find recent orders (last N days)
     */
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :since ORDER BY o.createdAt DESC")
    List<Order> findRecentOrders(@Param("since") LocalDateTime since);
}

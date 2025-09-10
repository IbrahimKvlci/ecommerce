package com.ibrahimkvlci.ecommerce.order.models;

/**
 * Enum representing the possible statuses of an order.
 */
public enum OrderStatus {
    PENDING,        // Order created but not yet confirmed
    CONFIRMED,      // Order confirmed and being processed
    PROCESSING,     // Order is being prepared
    SHIPPED,        // Order has been shipped
    DELIVERED,      // Order has been delivered
    CANCELLED,      // Order has been cancelled
    REFUNDED        // Order has been refunded
}

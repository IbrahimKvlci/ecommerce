# Order Management Module

This package provides comprehensive order management functionality for the e-commerce system.

## Package Structure

- **models**: Domain entities (Order, OrderItem, OrderStatus)
- **repositories**: Data access layer (OrderRepository, OrderItemRepository)
- **services**: Business logic (OrderService, OrderServiceImpl)
- **controllers**: REST API endpoints (OrderController)
- **dto**: Data transfer objects (OrderDTO, OrderItemDTO, CreateOrderRequest, UpdateOrderRequest)
- **exceptions**: Custom exceptions (OrderNotFoundException, OrderValidationException, etc.)

## API Endpoints

### Base URL: `/api/orders`

#### Order CRUD Operations
- `POST /` - Create a new order
- `GET /` - Get all orders
- `GET /{id}` - Get order by ID
- `GET /number/{orderNumber}` - Get order by order number
- `PUT /{id}` - Update order by ID
- `DELETE /{id}` - Delete order by ID

#### Order Management Operations
- `GET /customer/{customerId}` - Get orders by customer ID
- `GET /status/{status}` - Get orders by status
- `PATCH /{id}/status` - Update order status
- `PATCH /{id}/cancel` - Cancel an order
- `GET /{id}/can-cancel` - Check if order can be cancelled
- `GET /{id}/can-update` - Check if order can be updated

#### Search and Filter Operations
- `GET /date-range?startDate={date}&endDate={date}` - Get orders by date range
- `GET /amount-range?minAmount={amount}&maxAmount={amount}` - Get orders by amount range
- `GET /search/shipping-address?keyword={keyword}` - Search orders by shipping address

#### Statistics and Reporting
- `GET /statistics` - Get order statistics
- `GET /customer/{customerId}/history` - Get customer order history

## Order Status Flow

The order status follows a specific workflow:

1. **PENDING** - Order created but not yet confirmed
2. **CONFIRMED** - Order confirmed and being processed
3. **PROCESSING** - Order is being prepared
4. **SHIPPED** - Order has been shipped
5. **DELIVERED** - Order has been delivered
6. **CANCELLED** - Order has been cancelled
7. **REFUNDED** - Order has been refunded

### Valid Status Transitions

- PENDING → CONFIRMED, CANCELLED
- CONFIRMED → PROCESSING, CANCELLED
- PROCESSING → SHIPPED, CANCELLED
- SHIPPED → DELIVERED
- DELIVERED → REFUNDED
- CANCELLED, REFUNDED → (terminal states)

## Features

- **Input Validation**: Comprehensive validation using Bean Validation annotations
- **Error Handling**: Custom exceptions for better error management
- **Transaction Management**: All service methods are transactional
- **Order Status Management**: Enforced status transitions with validation
- **Search Capabilities**: Multiple search options including date range, amount range, and shipping address
- **Statistics**: Order statistics and reporting functionality
- **Data Transfer Objects**: Separate DTOs for API requests/responses
- **Repository Pattern**: Spring Data JPA repositories with custom query methods

## Usage Examples

### Create an Order

```java
CreateOrderRequest request = new CreateOrderRequest();
request.setCustomerId(1L);
request.setStatus(OrderStatus.PENDING);
request.setShippingAddress("123 Main St, City, State 12345");
request.setBillingAddress("123 Main St, City, State 12345");
request.setNotes("Please deliver during business hours");

List<CreateOrderRequest.CreateOrderItemRequest> items = new ArrayList<>();
CreateOrderRequest.CreateOrderItemRequest item = new CreateOrderRequest.CreateOrderItemRequest();
item.setProductId(1L);
item.setQuantity(2);
item.setUnitPrice(29.99);
items.add(item);
request.setOrderItems(items);

OrderDTO order = orderService.createOrder(request);
```

### Update Order Status

```java
OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, OrderStatus.CONFIRMED);
```

### Get Order Statistics

```java
OrderStatistics stats = orderService.getOrderStatistics();
System.out.println("Total Orders: " + stats.getTotalOrders());
System.out.println("Total Revenue: " + stats.getTotalRevenue());
```

## Dependencies

- `spring-boot-starter-data-jpa` - For JPA repository support
- `spring-boot-starter-web` - For REST API functionality
- `spring-boot-starter-validation` - For input validation
- `lombok` - For reducing boilerplate code

## Database Tables

- **orders**: Main order table with customer, status, and address information
- **order_items**: Order line items with product, quantity, and pricing information

## Error Handling

The module includes comprehensive error handling with custom exceptions:

- `OrderNotFoundException` - When order is not found
- `OrderValidationException` - When order validation fails
- `OrderItemNotFoundException` - When order item is not found
- `OrderStatusException` - When invalid status transition is attempted
- `InsufficientInventoryException` - When there's insufficient inventory

All exceptions are handled by the `OrderGlobalExceptionHandler` which returns appropriate HTTP status codes and error messages.

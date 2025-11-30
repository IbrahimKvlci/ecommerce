package com.ibrahimkvlci.ecommerce.order.controllers;

import com.ibrahimkvlci.ecommerce.order.exceptions.OrderNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderValidationException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderItemNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderStatusException;
import com.ibrahimkvlci.ecommerce.order.exceptions.InsufficientInventoryException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartItemNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CheckoutException;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.utils.results.ErrorResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.ErrorDataResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for Order module.
 * Handles all order-related exceptions and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class OrderGlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDataResult<Order>> handleOrderNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDataResult<Order>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ErrorDataResult<OrderItem>> handleOrderItemNotFoundException(OrderItemNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDataResult<OrderItem>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<ErrorResult> handleOrderValidationException(OrderValidationException ex) {
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<ErrorResult> handleOrderStatusException(OrderStatusException ex) {
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ErrorResult> handleInsufficientInventoryException(InsufficientInventoryException ex) {
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDataResult<Cart>> handleCartNotFoundException(CartNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDataResult<Cart>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorDataResult<CartItem>> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDataResult<CartItem>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CheckoutException.class)
    public ResponseEntity<ErrorResult> handleCheckoutException(CheckoutException ex) {
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.append(fieldName).append(": ").append(errorMessage).append("; ");
        });
        return new ResponseEntity<>(new ErrorResult(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ErrorResult("An unexpected error occurred: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

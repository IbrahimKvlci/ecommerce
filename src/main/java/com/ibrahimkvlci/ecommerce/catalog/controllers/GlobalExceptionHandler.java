package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.exceptions.ProductNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.ProductValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandValidationException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorResult;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.AuthException;

import java.util.Locale.Category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for catalog controllers.
 * Handles catalog-related exceptions and returns appropriate HTTP responses.
 */
@ControllerAdvice(basePackages = "com.ibrahimkvlci.ecommerce.catalog")
public class GlobalExceptionHandler {

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<ErrorDataResult<Product>> handleProductNotFoundException(ProductNotFoundException ex) {
                return new ResponseEntity<>(new ErrorDataResult<Product>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(ProductValidationException.class)
        public ResponseEntity<ErrorResult> handleProductValidationException(ProductValidationException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<ErrorDataResult<Category>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
                return new ResponseEntity<>(new ErrorDataResult<Category>(ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(CategoryValidationException.class)
        public ResponseEntity<ErrorResult> handleCategoryValidationException(CategoryValidationException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BrandNotFoundException.class)
        public ResponseEntity<ErrorDataResult<Brand>> handleBrandNotFoundException(BrandNotFoundException ex) {
                return new ResponseEntity<>(new ErrorDataResult<Brand>(ex.getMessage() + "test", null),
                                HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(BrandValidationException.class)
        public ResponseEntity<ErrorResult> handleBrandValidationException(BrandValidationException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InventoryNotFoundException.class)
        public ResponseEntity<ErrorDataResult<Inventory>> handleInventoryNotFoundException(
                        InventoryNotFoundException ex) {
                return new ResponseEntity<>(new ErrorDataResult<Inventory>(ex.getMessage(), null),
                                HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(InventoryValidationException.class)
        public ResponseEntity<ErrorResult> handleInventoryValidationException(InventoryValidationException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResult> handleIllegalArgumentException(IllegalArgumentException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AuthException.class)
        public ResponseEntity<ErrorResult> handleAuthException(AuthException ex) {
                return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.UNAUTHORIZED);
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

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResult> handleRuntimeException(RuntimeException ex) {
                return new ResponseEntity<>(new ErrorResult("An unexpected error occurred: " + ex.getMessage()),
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResult> handleGenericException(Exception ex) {
                return new ResponseEntity<>(new ErrorResult("An unexpected error occurred: " + ex.getMessage()),
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

}

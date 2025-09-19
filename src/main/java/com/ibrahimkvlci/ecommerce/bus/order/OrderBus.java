package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.CategoryBus;
import com.ibrahimkvlci.ecommerce.bus.catalog.InventoryBus;
import com.ibrahimkvlci.ecommerce.bus.catalog.ProductBus;
import com.ibrahimkvlci.ecommerce.bus.auth.CustomerAppBus;
import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderBus {

    private final ProductBus productBus;
    private final CategoryBus categoryBus;
    private final CustomerAppBus customerBus;
    private final InventoryBus inventoryBus;

    public ProductDTO getProductById(Long productId){
        return new ProductDTO(productBus.getProductById(productId).getId(), productBus.getProductById(productId).getTitle());
    }

    public boolean isProductAvailable(Long productId){
        return productBus.isProductAvailable(productId);
    }

    public CategoryDTO getCategoryById(Long categoryId){
        return new CategoryDTO(categoryBus.getCategoryById(categoryId).getId(), categoryBus.getCategoryById(categoryId).getName());
    }

    public boolean existsCategoryById(Long categoryId){
        return categoryBus.existsById(categoryId);
    }

    public CustomerDTO getCustomerById(Long customerId){
        return new CustomerDTO(
            customerBus.getCustomerById(customerId).getId(),
            customerBus.getCustomerById(customerId).getEmail(),
            customerBus.getCustomerById(customerId).getName(),
            customerBus.getCustomerById(customerId).getSurname()
        );
    }

    public CustomerDTO getCustomerByEmail(String email){
        return new CustomerDTO(
            customerBus.getCustomerByEmail(email).getId(),
            customerBus.getCustomerByEmail(email).getEmail(),
            customerBus.getCustomerByEmail(email).getName(),
            customerBus.getCustomerByEmail(email).getSurname()
        );
    }

    public boolean existsCustomerById(Long customerId){
        return customerBus.existsById(customerId);
    }

    public boolean existsCustomerByEmail(String email){
        return customerBus.existsByEmail(email);
    }

    public InventoryDTO getInventoryByProductIdAndSellerId(Long productId, Long sellerId){
        return new InventoryDTO(
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getId(),
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getProductId(),
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getQuantity(),
            sellerId,
            inventoryBus.getInventoryByProductIdAndSellerId(productId, sellerId).getPrice()
        );
    }

    public InventoryDTO updateInventory(Long id, int quantity, double price){
        com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO inventory = inventoryBus.updateInventory(id, quantity, price);

        return new InventoryDTO(
            inventory.getId(),
            inventory.getProductId(),
            inventory.getQuantity(),
            inventory.getSellerId(),
            inventory.getPrice()
        );
    }
}

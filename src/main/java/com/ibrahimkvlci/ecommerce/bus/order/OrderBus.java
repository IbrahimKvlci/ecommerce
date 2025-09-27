package com.ibrahimkvlci.ecommerce.bus.order;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.CategoryBus;
import com.ibrahimkvlci.ecommerce.bus.catalog.InventoryBus;
import com.ibrahimkvlci.ecommerce.bus.catalog.ProductBus;
import com.ibrahimkvlci.ecommerce.bus.payment.PaymentBus;
import com.ibrahimkvlci.ecommerce.bus.auth.CustomerAppBus;
import com.ibrahimkvlci.ecommerce.bus.auth.UserAppBus;
import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
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
    private final UserAppBus userAppBus;
    private final PaymentBus paymentBus;

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
            customerBus.getCustomerById(customerId).getSurname(),
            null
        );
    }

    public CustomerDTO getCustomerByEmail(String email){
        return new CustomerDTO(
            customerBus.getCustomerByEmail(email).getId(),
            customerBus.getCustomerByEmail(email).getEmail(),
            customerBus.getCustomerByEmail(email).getName(),
            customerBus.getCustomerByEmail(email).getSurname(),
            null
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

    public Long getCustomerIdFromJWT(){
        return userAppBus.getUserIdFromJWT();
    }

    public SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException,InvalidKeyException{
        var saleRequestNew=new com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest();
        // Convert and set fields for saleRequestNew using compatible types
        var cardCheckDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CardInfoDTO(
            saleRequest.getCardCheckDTO().getCardNumber(),
            saleRequest.getCardCheckDTO().getCardHolderName(),
            saleRequest.getCardCheckDTO().getExpirationDateYear(),
            saleRequest.getCardCheckDTO().getExpirationDateMonth(),
            saleRequest.getCardCheckDTO().getCvv()
        );

        var orderDTO = new com.ibrahimkvlci.ecommerce.payment.dto.OrderDTO(
            saleRequest.getOrderDTO().getOrderNumber(),
            saleRequest.getOrderDTO().getTotalAmount().toString(),
            saleRequest.getOrderDTO().getCurrencyCode(),
            saleRequest.getOrderDTO().getInstallCount()
        );

        var customerDTO = new com.ibrahimkvlci.ecommerce.payment.dto.CustomerDTO(
            saleRequest.getCustomerDTO().getId(),
            saleRequest.getCustomerDTO().getEmail(),
            saleRequest.getCustomerDTO().getIpAddress()
        );

        saleRequestNew.setCardInfoDTO(cardCheckDTO);
        saleRequestNew.setOrderDTO(orderDTO);
        saleRequestNew.setCustomerDTO(customerDTO);
        com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse saleResponse=paymentBus.sale(saleRequestNew);

        SaleResponse saleResponseNew=new SaleResponse();
        switch (saleResponse.getSaleStatusEnum()) {
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Error:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Error);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.Success:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.Success);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectURL:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectURL);
                break;
            case com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse.SaleStatusEnum.RedirectHTML:
                saleResponseNew.setSaleStatusEnum(SaleResponse.SaleStatusEnum.RedirectHTML);
                break;
            default:
                saleResponseNew.setSaleStatusEnum(null);
        }
        saleResponseNew.setHostMessage(saleResponse.getHostMessage());
        saleResponseNew.setHostResponseCode(saleResponse.getHostResponseCode());
        saleResponseNew.setResponseMessage(saleResponse.getResponseMessage());
        SaleResponse.Order order=new SaleResponse.Order();
        order.setOrderId(saleResponse.getOrder().getOrderId());
        saleResponseNew.setOrder(order);
        return saleResponseNew;
    }
}

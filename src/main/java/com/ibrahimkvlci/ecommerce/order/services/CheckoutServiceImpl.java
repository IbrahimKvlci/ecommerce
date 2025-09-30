package com.ibrahimkvlci.ecommerce.order.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibrahimkvlci.ecommerce.order.client.CustomerClient;
import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.client.PaymentClient;
import com.ibrahimkvlci.ecommerce.order.client.ProductClient;
import com.ibrahimkvlci.ecommerce.order.client.UserClient;
import com.ibrahimkvlci.ecommerce.order.dto.CardCheckDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.OrderNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CheckoutException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.models.Order;
import com.ibrahimkvlci.ecommerce.order.models.OrderItem;
import com.ibrahimkvlci.ecommerce.order.models.OrderStatus;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;
import com.ibrahimkvlci.ecommerce.order.repositories.OrderRepository;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {


    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;
    private final PaymentClient paymentClient;
    private final CustomerClient customerClient;

    @Override
    public SaleResponse checkoutPending(CheckoutRequestDTO request,String clientIp,RequestUtils.ClientType clientType) {
        Long cartId=cartRepository.findByCustomerId(userClient.getCustomerIdFromJWT()).get().getId();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            if(!productClient.isProductAvailable(cartItem.getProductId())){
                throw new CheckoutException(cartId, "Product with ID " + cartItem.getProductId() + " is not available");
            }
            InventoryDTO inventory = inventoryClient.getInventoryByProductIdAndSellerId(cartItem.getProductId(), cartItem.getSellerId());
            if(inventory.getQuantity() < cartItem.getQuantity()){
                throw new CheckoutException(cartId, "Insufficient inventory for product ID " + cartItem.getProductId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setSellerId(cartItem.getSellerId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(inventory.getPrice());
            orderItem.setTotalPrice(inventory.getPrice() * cartItem.getQuantity());
            orderItems.add(orderItem);
        }

        
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());
        order.setCustomerId(cart.getCustomerId());
        order.setOrderItems(orderItems);
        orderRepository.save(order);


        
        // Card info setters
        CardCheckDTO cardCheckDTO = new CardCheckDTO();
        cardCheckDTO.setCardNumber(request.getCardNumber());
        cardCheckDTO.setCardHolderName(request.getCardHolderName());
        cardCheckDTO.setExpirationDateYear(request.getCardExpireDateYear());
        cardCheckDTO.setExpirationDateMonth(request.getCardExpiteDateMonth());
        cardCheckDTO.setCvv(request.getCardCVV());
        
        // Order info setters
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getId().toString());
        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setCurrencyCode(949);
        orderDTO.setInstallCount(1);
        
        // Customer info setters
        CustomerDTO customerDTO = customerClient.getCustomerById(order.getCustomerId());
        customerDTO.setIpAddress(clientIp);

        if(!request.isSecured()){
            // SaleRequest setters
            SaleRequest saleRequest=new SaleRequest();
            saleRequest.setCardCheckDTO(cardCheckDTO);
            saleRequest.setOrderDTO(orderDTO);
            saleRequest.setCustomerDTO(customerDTO);
            SaleResponse saleResponse;
            try {
                saleResponse=paymentClient.sale(saleRequest);
            } catch (NoSuchAlgorithmException e) {
                throw new CheckoutException("Invalid Algorithm");
            }catch(InvalidKeyException ex){
                throw new CheckoutException("Invalid Secret Key");
            }
            return saleResponse;
        }
        // Secured (3D Pay) flow
        com.ibrahimkvlci.ecommerce.order.dto.SaleRequest saleRequest3D = new com.ibrahimkvlci.ecommerce.order.dto.SaleRequest();
        saleRequest3D.setCardCheckDTO(cardCheckDTO);
        saleRequest3D.setOrderDTO(orderDTO);
        saleRequest3D.setCustomerDTO(customerDTO);
        com.ibrahimkvlci.ecommerce.order.dto.SaleResponse saleResponse3D = paymentClient.sale3DPay(saleRequest3D);
        return saleResponse3D;
    }

    @Override
    public OrderDTO completeCheckout(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        for(OrderItem orderItem : order.getOrderItems()){
            InventoryDTO inventory = inventoryClient.getInventoryByProductIdAndSellerId(orderItem.getProductId(), orderItem.getSellerId());
            if(inventory.getQuantity() < orderItem.getQuantity()){
                throw new CheckoutException(orderId, "Insufficient inventory for product ID " + orderItem.getProductId(), true);
            }
        }
        for(OrderItem orderItem : order.getOrderItems()){
            InventoryDTO inventory = inventoryClient.getInventoryByProductIdAndSellerId(orderItem.getProductId(), orderItem.getSellerId());
            inventoryClient.updateInventory(inventory.getId(), inventory.getQuantity() - orderItem.getQuantity(), inventory.getPrice());
        }
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return orderService.getOrderById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + order.getId()));
    }

}

package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.client.UserClient;
import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;
import com.ibrahimkvlci.ecommerce.order.utils.results.DataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.Result;
import com.ibrahimkvlci.ecommerce.order.utils.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.order.utils.results.SuccessResult;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final InventoryClient inventoryClient;
    private final CartItemService cartItemService;

    private final UserClient userClient;

    @Override
    public DataResult<CartDTO> createCart(CreateCartRequest request) {
        // Check if cart already exists for customer
        if (cartRepository.existsByCustomerId(request.getCustomerId())) {
            throw new RuntimeException("Cart already exists for customer: " + request.getCustomerId());
        }

        Cart cart = new Cart();
        cart.setCustomerId(request.getCustomerId());

        Cart savedCart = cartRepository.save(cart);
        return new SuccessDataResult<CartDTO>(mapToDTO(savedCart));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CartDTO>> getAllCarts() {
        return new SuccessDataResult<List<CartDTO>>(cartRepository.findAll().stream()
                .map(cart -> mapToDTO(cart))
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CartDTO> getCartById(Long id) {
        return new SuccessDataResult<CartDTO>(cartRepository.findById(Objects.requireNonNull(id))
                .map(cart -> mapToDTO(cart))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CartDTO> getCartOfCustomer() {
        DataResult<Long> userIdRes = userClient.getCustomerIdFromJWT();
        if (!userIdRes.isSuccess()) {
            throw new AuthException("User not authenticated!");
        }
        Long customerId = userIdRes.getData();
        return new SuccessDataResult<CartDTO>(cartRepository.findByCustomerId(customerId)
                .map(cart -> mapToDTO(cart))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with customerId: " + customerId)));
    }

    @Override
    public Result deleteCart(Long id) {
        if (!cartRepository.existsById(Objects.requireNonNull(id))) {
            throw new CartNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("Cart deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<Double> calculateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(Objects.requireNonNull(cartId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        return new SuccessDataResult<Double>(cart.getCartItems().stream()
                .mapToDouble(item -> inventoryClient
                        .getInventoryByProductIdAndSellerId(item.getProductId(), item.getSellerId()).getPrice()
                        * item.getQuantity())
                .sum());
    }

    @Override
    public DataResult<CartDTO> clearCart(Long cartId) {
        Cart cart = cartRepository.findById(Objects.requireNonNull(cartId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        cart.getCartItems().clear();

        Cart savedCart = cartRepository.save(cart);
        return new SuccessDataResult<CartDTO>(mapToDTO(savedCart));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CartDTO> getCartWithItems(Long cartId) {
        return new SuccessDataResult<CartDTO>(cartRepository.findById(Objects.requireNonNull(cartId))
                .map(cart -> mapToDTO(cart))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CartDTO> getCartWithItemsByCustomerId(Long customerId) {
        return new SuccessDataResult<CartDTO>(cartRepository.findByCustomerId(customerId)
                .map(cart -> mapToDTO(cart))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with customerId: " + customerId)));
    }

    @Override
    public CartDTO mapToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(item -> inventoryClient
                        .getInventoryByProductIdAndSellerId(item.getProductId(), item.getSellerId()).getPrice()
                        * item.getQuantity())
                .sum());
        if (cart.getCartItems() != null) {
            cartDTO.setCartItems(cart.getCartItems().stream()
                    .map(cartItemService::mapToDTO)
                    .collect(Collectors.toList()));
        }

        return cartDTO;
    }

    @Override
    public Cart mapToEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        return cart;
    }
}

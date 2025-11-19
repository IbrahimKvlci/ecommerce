package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final InventoryClient inventoryClient;
    private final CartItemService cartItemService;

    @Override
    public CartDTO createCart(CreateCartRequest request) {
        // Check if cart already exists for customer
        if (cartRepository.existsByCustomerId(request.getCustomerId())) {
            throw new RuntimeException("Cart already exists for customer: " + request.getCustomerId());
        }

        Cart cart = new Cart();
        cart.setCustomerId(request.getCustomerId());

        Cart savedCart = cartRepository.save(cart);
        return mapToDTO(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartById(Long id) {
        return cartRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .map(this::mapToDTO);
    }

    @Override
    public void deleteCart(Long id) {
        if (!cartRepository.existsById(Objects.requireNonNull(id))) {
            throw new CartNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(Objects.requireNonNull(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(Objects.requireNonNull(cartId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        return cart.getCartItems().stream()
                .mapToDouble(item -> inventoryClient
                        .getInventoryByProductIdAndSellerId(item.getProductId(), item.getSellerId()).getPrice()
                        * item.getQuantity())
                .sum();
    }

    @Override
    public CartDTO clearCart(Long cartId) {
        Cart cart = cartRepository.findById(Objects.requireNonNull(cartId))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        cart.getCartItems().clear();

        Cart savedCart = cartRepository.save(cart);
        return mapToDTO(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartWithItems(Long cartId) {
        return cartRepository.findById(Objects.requireNonNull(cartId))
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartWithItemsByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .map(this::mapToDTO);
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

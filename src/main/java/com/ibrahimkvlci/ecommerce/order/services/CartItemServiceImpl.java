package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.client.InventoryClient;
import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartItemNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.repositories.CartItemRepository;
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
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    private final InventoryClient inventoryClient;

    @Override
    public CartItemDTO addCartItem(AddCartItemRequest request) {
        // Check if cart exists
        Cart cart = cartRepository.findById(Objects.requireNonNull(request.getCartId()))
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + request.getCartId()));

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductIdAndSellerId(
                request.getCartId(), request.getProductId(), request.getSellerId());

        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update existing item quantity
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setSellerId(request.getSellerId());
        }

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return mapToDTO(savedCartItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> getCartItemById(Long id) {
        return cartItemRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> getCartItemByCartIdAndProductIdAndSellerId(Long cartId, Long productId,
            Long sellerId) {
        return cartItemRepository.findByCartIdAndProductIdAndSellerId(cartId, productId, sellerId)
                .map(this::mapToDTO);
    }

    @Override
    public CartItemDTO updateCartItem(Long id, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + id));

        cartItem.setQuantity(request.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return mapToDTO(savedCartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(Objects.requireNonNull(id))) {
            throw new CartItemNotFoundException("Cart item not found with id: " + id);
        }
        cartItemRepository.deleteById(Objects.requireNonNull(id));
    }

    @Override
    public void deleteCartItemByCartIdAndProductId(Long cartId, Long productId) {
        if (!cartItemRepository.existsByCartIdAndProductId(cartId, productId)) {
            throw new CartItemNotFoundException(
                    "Cart item not found for cart: " + cartId + " and product: " + productId);
        }
        cartItemRepository.deleteByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void deleteCartItemsByCartId(Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cartItemExistsForCartAndProduct(Long cartId, Long productId) {
        return cartItemRepository.existsByCartIdAndProductId(cartId, productId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalQuantityByCartId(Long cartId) {
        Long totalQuantity = cartItemRepository.getTotalQuantityByCartId(cartId);
        return totalQuantity != null ? totalQuantity : 0L;
    }

    @Override
    public CartItemDTO mapToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        var inventory = inventoryClient.getInventoryByProductIdAndSellerId(cartItem.getProductId(),
                cartItem.getSellerId());
        cartItemDTO.setTotalPrice(inventory.getPrice() * cartItem.getQuantity());
        cartItemDTO.setInventory(inventory);
        return cartItemDTO;
    }

    @Override
    public CartItem mapToEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());

        return cartItem;
    }
}

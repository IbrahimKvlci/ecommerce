package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.AddCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.UpdateCartItemRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartItemNotFoundException;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.repositories.CartItemRepository;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public CartItemDTO addCartItem(AddCartItemRequest request) {
        // Check if cart exists
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + request.getCartId()));
        
        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(
                request.getCartId(), request.getProductId());
        
        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update existing item quantity
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setPrice(request.getPrice());
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(request.getPrice());
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
        return cartItemRepository.findById(id)
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
    public Optional<CartItemDTO> getCartItemByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .map(this::mapToDTO);
    }
    
    @Override
    public CartItemDTO updateCartItem(Long id, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + id));
        
        cartItem.setQuantity(request.getQuantity());
        
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return mapToDTO(savedCartItem);
    }
    
    @Override
    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException("Cart item not found with id: " + id);
        }
        cartItemRepository.deleteById(id);
    }
    
    @Override
    public void deleteCartItemByCartIdAndProductId(Long cartId, Long productId) {
        if (!cartItemRepository.existsByCartIdAndProductId(cartId, productId)) {
            throw new CartItemNotFoundException("Cart item not found for cart: " + cartId + " and product: " + productId);
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
    @Transactional(readOnly = true)
    public Double getTotalPriceByCartId(Long cartId) {
        Double totalPrice = cartItemRepository.getTotalPriceByCartId(cartId);
        return totalPrice != null ? totalPrice : 0.0;
    }
    
    @Override
    public CartItemDTO mapToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setCartId(cartItem.getCart().getId());
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
        cartItemDTO.setCreatedAt(cartItem.getCreatedAt());
        cartItemDTO.setUpdatedAt(cartItem.getUpdatedAt());
        
        return cartItemDTO;
    }
    
    @Override
    public CartItem mapToEntity(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());
        cartItem.setTotalPrice(cartItemDTO.getTotalPrice());
        cartItem.setCreatedAt(cartItemDTO.getCreatedAt());
        cartItem.setUpdatedAt(cartItemDTO.getUpdatedAt());
        
        return cartItem;
    }
}

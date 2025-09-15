package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CartItemDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.exceptions.CartNotFoundException;
import com.ibrahimkvlci.ecommerce.order.models.Cart;
import com.ibrahimkvlci.ecommerce.order.models.CartItem;
import com.ibrahimkvlci.ecommerce.order.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public CartDTO createCart(CreateCartRequest request) {
        // Check if cart already exists for customer
        if (cartRepository.existsByCustomerId(request.getCustomerId())) {
            throw new RuntimeException("Cart already exists for customer: " + request.getCustomerId());
        }
        
        Cart cart = new Cart();
        cart.setCustomerId(request.getCustomerId());
        cart.setTotalPrice(0.0);
        
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
        return cartRepository.findById(id)
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
        if (!cartRepository.existsById(id)) {
            throw new CartNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double calculateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getTotalPrice() != null ? item.getTotalPrice() : 0.0)
                .sum();
    }
    
    @Override
    public CartDTO clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        
        Cart savedCart = cartRepository.save(cart);
        return mapToDTO(savedCart);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartWithItems(Long cartId) {
        return cartRepository.findById(cartId)
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
        cartDTO.setCustomerId(cart.getCustomerId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());
        
        if (cart.getCartItems() != null) {
            cartDTO.setCartItems(cart.getCartItems().stream()
                    .map(this::mapCartItemToDTO)
                    .collect(Collectors.toList()));
        }
        
        return cartDTO;
    }
    
    @Override
    public Cart mapToEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setCustomerId(cartDTO.getCustomerId());
        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setCreatedAt(cartDTO.getCreatedAt());
        cart.setUpdatedAt(cartDTO.getUpdatedAt());
        
        return cart;
    }
    
    private CartItemDTO mapCartItemToDTO(CartItem cartItem) {
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
}

package com.demo.spring.service;

import com.demo.spring.dao.CartItemRepository;
import com.demo.spring.dao.CartRepository;
import com.demo.spring.dao.ProductRepository;
import com.demo.spring.dao.UserRepository;
import com.demo.spring.entity.Cart;
import com.demo.spring.entity.CartItem;
import com.demo.spring.entity.User;
import com.demo.spring.entity.Product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Create a new cart for a user
    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    // Add product to cart
    public CartItem addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

    // Get all items in a cart
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getItems();
    }

    // Remove an item from cart
    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    // Clear entire cart
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepository.deleteAll(cart.getItems());
    }
    // Update cart item quantity
    public CartItem updateCartItem(Long cartId, Long itemId, int quantity) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
		CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Cart item not found"));

		if (!item.getCart().getId().equals(cart.getId())) {
			throw new RuntimeException("Item does not belong to this cart");
		}

		item.setQuantity(quantity);
		return cartItemRepository.save(item);
	}
    
}

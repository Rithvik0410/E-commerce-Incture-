package com.demo.spring.controller;

import com.demo.spring.dto.CartItemDTO;
import com.demo.spring.entity.Cart;
import com.demo.spring.entity.CartItem;
import com.demo.spring.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	// Create cart for user
	@PostMapping("/create/{userId}")
	public Cart createCart(@PathVariable Long userId) {
		return cartService.createCart(userId);
	}

	// Add product to cart
	@PostMapping("/{cartId}/add/{productId}")
	public CartItem addToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
		return cartService.addToCart(cartId, productId, quantity);
	}

	// View cart items
	@GetMapping("/{cartId}/items")
	public List<CartItemDTO> getCartItems(@PathVariable Long cartId) {
		return cartService.getCartItems(cartId).stream().map(CartItemDTO::new).toList();
	}

	// Remove specific item
	@DeleteMapping("/item/{itemId}")
	public void removeItem(@PathVariable Long itemId) {
		cartService.removeItem(itemId);
	}

	// Clear cart
	@DeleteMapping("/{cartId}/clear")
	public void clearCart(@PathVariable Long cartId) {
		cartService.clearCart(cartId);
	}
	
   // Update cart item quantity
	@PutMapping("/{cartId}/update/{itemId}")
	public CartItem updateCartItem(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity) {
		return cartService.updateCartItem(cartId, itemId, quantity);
	}
}
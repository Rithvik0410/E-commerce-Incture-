package com.demo.spring.util;

import com.demo.spring.entity.*;

import java.util.ArrayList;


/**
 * Utility class for creating test data
 */
public class TestDataFactory {

    /**
     * Create a test user
     */
    public static User createTestUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
    
    /**
     * Create a test product
     */
    public static Product createTestProduct(Long id, String name, String description, double price, int quantity) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }
    
    /**
     * Create a test cart
     */
    public static Cart createTestCart(Long id, User user) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        return cart;
    }
    
    /**
     * Create a test cart item
     */
    public static CartItem createTestCartItem(Long id, Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItem;
    }
    
    /**
     * Create a test order
     */
    public static Order createTestOrder(Long id, User user, String status, double totalPrice) {
        Order order = new Order();
        order.setId(id);
        order.setUser(user);
        order.setStatus(status);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(new ArrayList<>());
        return order;
    }
    
    /**
     * Create a test order item
     */
    public static OrderItem createTestOrderItem(Long id, Order order, Product product, int quantity, double price) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(price);
        return orderItem;
    }
    
    /**
     * Create a test payment
     */
    public static Payment createTestPayment(Long id, Order order, String method, String status) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setOrder(order);
        payment.setMethod(method);
        payment.setStatus(status);
        return payment;
    }
    
    /**
     * Create a test review
     */
    public static Review createTestReview(Long id, Product product, int rating, String comment) {
        Review review = new Review();
        review.setId(id);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }
}

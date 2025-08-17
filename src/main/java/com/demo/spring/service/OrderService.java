package com.demo.spring.service;

import com.demo.spring.dao.OrderRepository;
import com.demo.spring.dao.CartRepository;
import com.demo.spring.entity.Cart;
import com.demo.spring.entity.CartItem;
import com.demo.spring.entity.Order;
import com.demo.spring.entity.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                       CartRepository cartRepository,
                       CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    // Create order from cart (move to billing stage)
    @Transactional
    public Order createOrderFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cannot create order from empty cart");
        }

        // Create new order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("PENDING");

        // Calculate total price and create order items
        double totalPrice = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            
            orderItems.add(orderItem);
            totalPrice += orderItem.getPrice() * orderItem.getQuantity();
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after successful order creation
        cartService.clearCart(cartId);

        return savedOrder;
    }

    // Get all orders for a user
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Update order status (for payment processing)
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

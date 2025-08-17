package com.demo.spring.unit.service;

import com.demo.spring.dao.CartRepository;
import com.demo.spring.dao.OrderRepository;
import com.demo.spring.entity.*;
import com.demo.spring.service.CartService;
import com.demo.spring.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    private Cart testCart;
    private User testUser;
    private Product testProduct;
    private CartItem testCartItem;
    private Order testOrder;
    private List<CartItem> cartItems;

    @BeforeEach
    void setUp() {
        // Setup test data
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);

        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);

        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setCart(testCart);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);

        cartItems = new ArrayList<>();
        cartItems.add(testCartItem);
        testCart.setItems(cartItems);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setStatus("PENDING");
        testOrder.setTotalPrice(200.0);
    }

    @Test
    void createOrderFromCart_WithValidCartId_ShouldCreateOrder() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        doNothing().when(cartService).clearCart(1L);

        // Act
        Order createdOrder = orderService.createOrderFromCart(1L);

        // Assert
        assertNotNull(createdOrder);
        assertEquals("PENDING", createdOrder.getStatus());
        assertEquals(testUser.getId(), createdOrder.getUser().getId());
        verify(cartRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartService, times(1)).clearCart(1L);
    }

    @Test
    void createOrderFromCart_WithEmptyCart_ShouldThrowException() {
        // Arrange
        testCart.setItems(new ArrayList<>());
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrderFromCart(1L);
        });
        assertTrue(exception.getMessage().contains("empty cart"));
        verify(cartRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
        verify(cartService, never()).clearCart(anyLong());
    }

    @Test
    void createOrderFromCart_WithInvalidCartId_ShouldThrowException() {
        // Arrange
        when(cartRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrderFromCart(999L);
        });
        assertTrue(exception.getMessage().contains("Cart not found"));
        verify(cartRepository, times(1)).findById(999L);
        verify(orderRepository, never()).save(any(Order.class));
        verify(cartService, never()).clearCart(anyLong());
    }

    @Test
    void getOrderById_WithValidId_ShouldReturnOrder() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Order order = orderService.getOrderById(1L);

        // Assert
        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals("PENDING", order.getStatus());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(999L);
        });
        assertTrue(exception.getMessage().contains("not found"));
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void getOrdersByUserId_ShouldReturnUserOrders() {
        // Arrange
        when(orderRepository.findByUserId(1L)).thenReturn(Arrays.asList(testOrder));

        // Act
        List<Order> orders = orderService.getOrdersByUserId(1L);

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getId());
        verify(orderRepository, times(1)).findByUserId(1L);
    }

    @Test
    void updateOrderStatus_WithValidOrderId_ShouldUpdateStatus() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        testOrder.setStatus("PAID");
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        // Act
        Order updatedOrder = orderService.updateOrderStatus(1L, "PAID");

        // Assert
        assertNotNull(updatedOrder);
        assertEquals("PAID", updatedOrder.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(testOrder);
    }
}

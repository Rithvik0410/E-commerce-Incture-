package com.demo.spring.unit.controller;

import com.demo.spring.controller.OrderController;
import com.demo.spring.entity.Order;
import com.demo.spring.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setStatus("PENDING");
        testOrder.setTotalPrice(200.0);
    }

    @Test
    void createOrderFromCart_WithValidCartId_ShouldReturnOrder() {
        // Arrange
        when(orderService.createOrderFromCart(1L)).thenReturn(testOrder);

        // Act
        ResponseEntity<Order> response = orderController.createOrderFromCart(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(orderService, times(1)).createOrderFromCart(1L);
    }

    @Test
    void createOrderFromCart_WithInvalidCartId_ShouldReturnBadRequest() {
        // Arrange
        when(orderService.createOrderFromCart(anyLong()))
            .thenThrow(new RuntimeException("Cart not found"));

        // Act
        ResponseEntity<Order> response = orderController.createOrderFromCart(999L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).createOrderFromCart(999L);
    }

    @Test
    void getOrdersByUserId_ShouldReturnListOfOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(testOrder);
        when(orderService.getOrdersByUserId(1L)).thenReturn(orders);

        // Act
        ResponseEntity<List<Order>> response = orderController.getOrdersByUserId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(orderService, times(1)).getOrdersByUserId(1L);
    }

    @Test
    void getOrderById_WithValidId_ShouldReturnOrder() {
        // Arrange
        when(orderService.getOrderById(1L)).thenReturn(testOrder);

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void getOrderById_WithInvalidId_ShouldReturnNotFound() {
        // Arrange
        when(orderService.getOrderById(anyLong()))
            .thenThrow(new RuntimeException("Order not found"));

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).getOrderById(999L);
    }

    @Test
    void updateOrderStatus_WithValidData_ShouldReturnUpdatedOrder() {
        // Arrange
        testOrder.setStatus("PAID");
        when(orderService.updateOrderStatus(1L, "PAID")).thenReturn(testOrder);

        // Act
        ResponseEntity<Order> response = orderController.updateOrderStatus(1L, "PAID");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PAID", response.getBody().getStatus());
        verify(orderService, times(1)).updateOrderStatus(1L, "PAID");
    }

    @Test
    void updateOrderStatus_WithInvalidOrderId_ShouldReturnNotFound() {
        // Arrange
        when(orderService.updateOrderStatus(anyLong(), anyString()))
            .thenThrow(new RuntimeException("Order not found"));

        // Act
        ResponseEntity<Order> response = orderController.updateOrderStatus(999L, "PAID");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).updateOrderStatus(999L, "PAID");
    }
}

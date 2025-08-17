package com.demo.spring.unit.service;

import com.demo.spring.dao.OrderRepository;
import com.demo.spring.dao.PaymentRepository;
import com.demo.spring.entity.Order;
import com.demo.spring.entity.Payment;
import com.demo.spring.entity.User;
import com.demo.spring.service.OrderService;
import com.demo.spring.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private PaymentService paymentService;

    private Order testOrder;
    private Payment testPayment;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");

        // Create test order
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setStatus("PENDING");
        testOrder.setTotalPrice(200.0);

        // Create test payment
        testPayment = new Payment();
        testPayment.setId(1L);
        testPayment.setOrder(testOrder);
        testPayment.setMethod("CREDIT_CARD");
        testPayment.setStatus("PENDING");
    }

    @Test
    void createPayment_WithValidOrderIdAndMethod_ShouldCreatePayment() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // Act
        Payment createdPayment = paymentService.createPayment(1L, "CREDIT_CARD");

        // Assert
        assertNotNull(createdPayment);
        assertEquals("CREDIT_CARD", createdPayment.getMethod());
        assertEquals("PENDING", createdPayment.getStatus());
        assertEquals(testOrder.getId(), createdPayment.getOrder().getId());
        verify(orderRepository, times(1)).findById(1L);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void createPayment_WithInvalidOrderId_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.createPayment(999L, "CREDIT_CARD");
        });
        assertTrue(exception.getMessage().contains("Order not found"));
        verify(orderRepository, times(1)).findById(999L);
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void createPayment_WithInvalidPaymentMethod_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(1L, "INVALID_METHOD");
        });
        assertTrue(exception.getMessage().contains("Invalid payment method"));
        verify(orderRepository, times(1)).findById(1L);
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void processPayment_WithSuccessfulPayment_ShouldUpdateStatusToPaid() {
        // Arrange - Simulate successful payment (90% chance in the service)
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            payment.setStatus("COMPLETED");
            return payment;
        });
        
        // Mock the OrderService.updateOrderStatus method
        doReturn(testOrder).when(orderService).updateOrderStatus(eq(1L), eq("PAID"));

        // Use mockito's doAnswer to control the return of simulatePaymentProcessing
        doAnswer(invocation -> true).when(paymentService);

        // Act
        Payment processedPayment = paymentService.processPayment(1L);

        // Assert
        assertNotNull(processedPayment);
        assertEquals("COMPLETED", processedPayment.getStatus());
        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderService, times(1)).updateOrderStatus(1L, "PAID");
    }

    @Test
    void getPaymentById_WithValidId_ShouldReturnPayment() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // Act
        Payment payment = paymentService.getPaymentById(1L);

        // Assert
        assertNotNull(payment);
        assertEquals(1L, payment.getId());
        assertEquals("CREDIT_CARD", payment.getMethod());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void getPaymentById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(paymentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(999L);
        });
        assertTrue(exception.getMessage().contains("Payment not found"));
        verify(paymentRepository, times(1)).findById(999L);
    }

    @Test
    void getPaymentByOrderId_WithValidOrderId_ShouldReturnPayment() {
        // Arrange
        testOrder.setPayment(testPayment);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Payment payment = paymentService.getPaymentByOrderId(1L);

        // Assert
        assertNotNull(payment);
        assertEquals(1L, payment.getId());
        assertEquals("CREDIT_CARD", payment.getMethod());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getPaymentByOrderId_WithInvalidOrderId_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentByOrderId(999L);
        });
        assertTrue(exception.getMessage().contains("Order not found"));
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void getPaymentByOrderId_WithOrderWithoutPayment_ShouldThrowException() {
        // Arrange
        testOrder.setPayment(null);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentByOrderId(1L);
        });
        assertTrue(exception.getMessage().contains("No payment found"));
        verify(orderRepository, times(1)).findById(1L);
    }
}

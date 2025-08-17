package com.demo.spring.service;

import com.demo.spring.dao.PaymentRepository;
import com.demo.spring.dao.OrderRepository;
import com.demo.spring.entity.Payment;
import com.demo.spring.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }
    
    // Create a new payment for an order
    @Transactional
    public Payment createPayment(Long orderId, String paymentMethod) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            
        // Check if order already has a payment
        if (order.getPayment() != null) {
            throw new RuntimeException("Payment already exists for this order");
        }
        
        // Validate payment method
        if (!isValidPaymentMethod(paymentMethod)) {
            throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(paymentMethod);
        payment.setStatus("PENDING");
        
        Payment savedPayment = paymentRepository.save(payment);
        
        return savedPayment;
    }
    
    // Process a payment (simulate payment processing)
    @Transactional
    public Payment processPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        
        // Simulate payment processing
        boolean paymentSuccessful = simulatePaymentProcessing();
        
        if (paymentSuccessful) {
            payment.setStatus("COMPLETED");
            
            // Update order status to PAID
            Order order = payment.getOrder();
            orderService.updateOrderStatus(order.getId(), "PAID");
        } else {
            payment.setStatus("FAILED");
        }
        
        return paymentRepository.save(payment);
    }
    
    // Get payment by ID
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
    }
    
    // Get payment for a specific order
    public Payment getPaymentByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            
        Payment payment = order.getPayment();
        if (payment == null) {
            throw new RuntimeException("No payment found for order with id: " + orderId);
        }
        
        return payment;
    }
    
    // Private helper methods
    private boolean isValidPaymentMethod(String paymentMethod) {
        return paymentMethod != null && 
               (paymentMethod.equals("CREDIT_CARD") || 
                paymentMethod.equals("DEBIT_CARD") || 
                paymentMethod.equals("PAYPAL") || 
                paymentMethod.equals("UPI"));
    }
    
    private boolean simulatePaymentProcessing() {
        // In a real application, this would integrate with a payment gateway
        // For this demo, we'll just simulate a successful payment most of the time
        return Math.random() < 0.9; // 90% success rate
    }
}

package com.demo.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method; // CREDIT_CARD, PAYPAL, etc.
    private String status; // PENDING, COMPLETED, FAILED

    // Payment belongs to one Order
    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}

package com.demo.spring.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One user can have multiple carts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Cart> carts;

    // One user can have multiple orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Order> orders;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Cart> getCarts() { return carts; }
    public void setCarts(List<Cart> carts) { this.carts = carts; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}

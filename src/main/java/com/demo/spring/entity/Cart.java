package com.demo.spring.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cart belongs to one User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Cart has many CartItems
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<CartItem> items;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
}

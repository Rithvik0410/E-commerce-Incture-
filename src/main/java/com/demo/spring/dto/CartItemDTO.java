package com.demo.spring.dto;

import com.demo.spring.entity.CartItem;

public class CartItemDTO {
    private Long id;
    private int quantity;
    private String productName;
    private double productPrice;

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.productName = cartItem.getProduct().getName();
        this.productPrice = cartItem.getProduct().getPrice();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

    // getters
}

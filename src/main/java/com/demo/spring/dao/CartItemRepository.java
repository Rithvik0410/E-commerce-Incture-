package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	// Additional query methods can be defined here if needed

}

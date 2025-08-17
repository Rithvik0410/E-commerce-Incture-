package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	// Additional query methods can be defined here if needed

}

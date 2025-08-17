package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	// Additional query methods can be defined here if needed

}

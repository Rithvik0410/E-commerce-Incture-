package com.demo.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	// Additional query methods can be defined here if needed
	List<Order> findByUserId(Long userId);

}

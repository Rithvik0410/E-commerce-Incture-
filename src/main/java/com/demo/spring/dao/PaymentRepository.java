package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	// Additional query methods can be defined here if needed

}

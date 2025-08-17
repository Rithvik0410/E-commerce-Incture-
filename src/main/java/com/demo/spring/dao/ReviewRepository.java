package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	// Additional query methods can be defined here if needed

}

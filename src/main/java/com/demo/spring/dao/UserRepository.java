package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Additional query methods can be defined here if needed

}

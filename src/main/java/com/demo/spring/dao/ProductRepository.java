package com.demo.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

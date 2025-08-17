package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dao.ProductRepository;
import com.demo.spring.entity.Product;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	public Product getProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		Product p = product.get();
		return p;
	}

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}

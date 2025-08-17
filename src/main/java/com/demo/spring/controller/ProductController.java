package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Product;
import com.demo.spring.service.ProductService;


@ResponseBody
@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/products")
	public List<Product> getAllProducts() {
		List<Product> products = this.productService.getAllProducts();
		return products;
	}
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") Long id) {
		return this.productService.getProductById(id);
	}
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return this.productService.addProduct(product);
	}
	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable("id") Long id,@RequestBody Product product) {
		product.setId(id);
		return this.productService.updateProduct(product);
	}
	@DeleteMapping("/products/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		this.productService.deleteProduct(id);
		return "Product with ID " + id + " deleted successfully.";
	}
	  

}

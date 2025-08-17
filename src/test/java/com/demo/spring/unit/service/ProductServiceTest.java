package com.demo.spring.unit.service;

import com.demo.spring.dao.ProductRepository;
import com.demo.spring.entity.Product;
import com.demo.spring.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Setup a test product
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(100.0);
        testProduct.setQuantity(10);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_WithValidId_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        Product product = productService.getProductById(1L);

        // Assert
        assertNotNull(product);
        assertEquals("Test Product", product.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productService.getProductById(999L);
        });
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        Product newProduct = new Product();
        newProduct.setName("Test Product");
        newProduct.setDescription("Test Description");
        newProduct.setPrice(100.0);
        newProduct.setQuantity(10);
        
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        Product savedProduct = productService.addProduct(newProduct);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_WithValidId_ShouldUpdateAndReturnProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(200.0);
        updatedProduct.setQuantity(20);
        
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(200.0, result.getPrice());
        assertEquals(20, result.getQuantity());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct_WithValidId_ShouldCallRepositoryDelete() {
        // Arrange
        doNothing().when(productRepository).deleteById(1L);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_WithInvalidId_ShouldStillCallDelete() {
        // Arrange
        doNothing().when(productRepository).deleteById(999L);

        // Act
        productService.deleteProduct(999L);

        // Assert
        verify(productRepository, times(1)).deleteById(999L);
    }
}

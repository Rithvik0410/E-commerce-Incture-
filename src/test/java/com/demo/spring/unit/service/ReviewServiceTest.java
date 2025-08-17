package com.demo.spring.unit.service;

import com.demo.spring.dao.ProductRepository;
import com.demo.spring.dao.ReviewRepository;
import com.demo.spring.entity.Product;
import com.demo.spring.entity.Review;
import com.demo.spring.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Product testProduct;
    private Review testReview;
    private List<Review> testReviews;

    @BeforeEach
    void setUp() {
        // Create test product
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        
        // Create test review
        testReview = new Review();
        testReview.setId(1L);
        testReview.setProduct(testProduct);
        testReview.setRating(5);
        testReview.setComment("Great product!");
        
        // Create list of reviews
        testReviews = new ArrayList<>();
        testReviews.add(testReview);
        
        // Set reviews to product
        testProduct.setReviews(testReviews);
    }

    @Test
    void addReview_WithValidData_ShouldCreateReview() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        Review createdReview = reviewService.addReview(1L, 5, "Great product!");

        // Assert
        assertNotNull(createdReview);
        assertEquals(5, createdReview.getRating());
        assertEquals("Great product!", createdReview.getComment());
        assertEquals(testProduct.getId(), createdReview.getProduct().getId());
        verify(productRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void addReview_WithInvalidProductId_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.addReview(999L, 5, "Great product!");
        });
        assertTrue(exception.getMessage().contains("Product not found"));
        verify(productRepository, times(1)).findById(999L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void addReview_WithInvalidRating_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act & Assert - Rating too low
        Exception exceptionLow = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReview(1L, 0, "Bad rating");
        });
        assertTrue(exceptionLow.getMessage().contains("Rating must be between 1 and 5"));

        // Act & Assert - Rating too high
        Exception exceptionHigh = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReview(1L, 6, "Bad rating");
        });
        assertTrue(exceptionHigh.getMessage().contains("Rating must be between 1 and 5"));
        
        verify(productRepository, times(2)).findById(1L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getProductReviews_ShouldReturnReviews() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        List<Review> reviews = reviewService.getProductReviews(1L);

        // Assert
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(5, reviews.get(0).getRating());
        assertEquals("Great product!", reviews.get(0).getComment());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductReviews_WithInvalidProductId_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.getProductReviews(999L);
        });
        assertTrue(exception.getMessage().contains("Product not found"));
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void getReviewById_WithValidId_ShouldReturnReview() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));

        // Act
        Review review = reviewService.getReviewById(1L);

        // Assert
        assertNotNull(review);
        assertEquals(1L, review.getId());
        assertEquals(5, review.getRating());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void getReviewById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.getReviewById(999L);
        });
        assertTrue(exception.getMessage().contains("Review not found"));
        verify(reviewRepository, times(1)).findById(999L);
    }

    @Test
    void updateReview_WithValidData_ShouldUpdateReview() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        Review updatedReview = reviewService.updateReview(1L, 4, "Updated comment");

        // Assert
        assertNotNull(updatedReview);
        assertEquals(4, updatedReview.getRating());
        assertEquals("Updated comment", updatedReview.getComment());
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void updateReview_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(999L, 4, "Updated comment");
        });
        assertTrue(exception.getMessage().contains("Review not found"));
        verify(reviewRepository, times(1)).findById(999L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void updateReview_WithInvalidRating_ShouldThrowException() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));

        // Act & Assert - Rating too low
        Exception exceptionLow = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(1L, 0, "Bad rating");
        });
        assertTrue(exceptionLow.getMessage().contains("Rating must be between 1 and 5"));

        // Act & Assert - Rating too high
        Exception exceptionHigh = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(1L, 6, "Bad rating");
        });
        assertTrue(exceptionHigh.getMessage().contains("Rating must be between 1 and 5"));
        
        verify(reviewRepository, times(2)).findById(1L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_WithValidId_ShouldDeleteReview() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        doNothing().when(reviewRepository).delete(any(Review.class));

        // Act
        reviewService.deleteReview(1L);

        // Assert
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).delete(any(Review.class));
    }

    @Test
    void deleteReview_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview(999L);
        });
        assertTrue(exception.getMessage().contains("Review not found"));
        verify(reviewRepository, times(1)).findById(999L);
        verify(reviewRepository, never()).delete(any(Review.class));
    }
}

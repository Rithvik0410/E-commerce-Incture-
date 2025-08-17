package com.demo.spring.service;

import com.demo.spring.dao.ReviewRepository;
import com.demo.spring.dao.ProductRepository;
import com.demo.spring.entity.Review;
import com.demo.spring.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }
    
    // Add a review to a product
    @Transactional
    public Review addReview(Long productId, int rating, String comment) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            
        // Validate rating is between 1 and 5
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        Review review = new Review();
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        
        return reviewRepository.save(review);
    }
    
    // Get all reviews for a product
    public List<Review> getProductReviews(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            
        return product.getReviews();
    }
    
    // Get a specific review by ID
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    }
    
    // Update an existing review
    @Transactional
    public Review updateReview(Long reviewId, int rating, String comment) {
        Review review = getReviewById(reviewId);
        
        // Validate rating is between 1 and 5
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        review.setRating(rating);
        review.setComment(comment);
        
        return reviewRepository.save(review);
    }
    
    // Delete a review
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        reviewRepository.delete(review);
    }
}

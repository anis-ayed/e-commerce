package com.spring.angular.ecommerce.services.customer.review;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;
import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.dto.ReviewDto;
import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.entities.Review;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.repositories.OrderRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import com.spring.angular.ecommerce.repositories.ReviewRepository;
import com.spring.angular.ecommerce.repositories.UserRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;

  public OrderedProductsResponseDto getOrderedProductsResponseDto(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(
                () -> new IllegalArgumentException("Order not found with id : " + orderId));

    List<ProductDto> productDtoList =
        order.getCartItems().stream()
            .map(
                cartItems ->
                    ProductDto.builder()
                        .id(cartItems.getProduct().getId())
                        .name(cartItems.getProduct().getName())
                        .description(cartItems.getProduct().getDescription())
                        .price(cartItems.getProduct().getPrice())
                        .byteImg(cartItems.getProduct().getImg())
                        .categoryId(cartItems.getProduct().getCategory().getId())
                        .categoryName(cartItems.getProduct().getCategory().getName())
                        .quantity(cartItems.getQuantity())
                        .build())
            .toList();

    return OrderedProductsResponseDto.builder()
        .orderAmount(order.getAmount())
        .productDtoList(productDtoList)
        .build();
  }

  public ReviewDto setReview(ReviewDto reviewDto) throws IOException {
    Product product =
        productRepository
            .findById(reviewDto.getProductId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Product not found with id: " + reviewDto.getProductId()));

    User user =
        userRepository
            .findById(reviewDto.getUserId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "User not found with userId: " + reviewDto.getUserId()));

    Review review =
        Review.builder()
            .rating(reviewDto.getRating())
            .description(reviewDto.getDescription())
            .user(user)
            .product(product)
            .img(reviewDto.getImg().getBytes())
            .build();
    return reviewRepository.save(review).getReviewDto();
  }
}

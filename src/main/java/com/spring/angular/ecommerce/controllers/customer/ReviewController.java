package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;
import com.spring.angular.ecommerce.services.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping("/ordered-product/{orderId}")
  public ResponseEntity<OrderedProductsResponseDto> getOrderedProductDetailsByOrderId(
      @PathVariable Long orderId) {
    return ResponseEntity.ok(reviewService.getOrderedProductsResponseDto(orderId));
  }
}

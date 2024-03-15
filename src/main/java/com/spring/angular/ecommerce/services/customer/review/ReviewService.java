package com.spring.angular.ecommerce.services.customer.review;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;
import com.spring.angular.ecommerce.dto.ReviewDto;
import java.io.IOException;

public interface ReviewService {
  OrderedProductsResponseDto getOrderedProductsResponseDto(Long orderId);

  ReviewDto setReview(ReviewDto reviewDto) throws IOException;
}

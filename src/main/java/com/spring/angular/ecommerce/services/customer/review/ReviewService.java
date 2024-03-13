package com.spring.angular.ecommerce.services.customer.review;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;

public interface ReviewService {
  OrderedProductsResponseDto getOrderedProductsResponseDto(Long orderId);
}

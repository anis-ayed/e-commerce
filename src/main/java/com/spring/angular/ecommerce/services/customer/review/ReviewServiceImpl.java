package com.spring.angular.ecommerce.services.customer.review;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;
import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.repositories.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final OrderRepository orderRepository;

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
}

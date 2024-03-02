package com.spring.angular.ecommerce.services.customer.cart;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
  ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCArtDto);

  OrderDto getCartByUserId(Long userId);
}

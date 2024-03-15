package com.spring.angular.ecommerce.services.customer.cart;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.dto.PlaceOrderDto;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface CartService {
  ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCArtDto);

  OrderDto getCartByUserId(Long userId);

  OrderDto applyCoupon(Long userId, String code);

  OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

  OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

  OrderDto placeOrder(PlaceOrderDto placeOrderDto);

  List<OrderDto> getMyPlacedOrders(Long userId);

  OrderDto searchOrderByTrackingId(UUID trackingId);
}

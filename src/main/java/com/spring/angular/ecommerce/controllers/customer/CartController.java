package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @PostMapping("/cart")
  public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
    return cartService.addProductToCart(addProductInCartDto);
  }

  @GetMapping("/cart/{userId}")
  public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
  }
}

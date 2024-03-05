package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.dto.PlaceOrderDto;
import com.spring.angular.ecommerce.exceptions.ValidationException;
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
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.addProductToCart(addProductInCartDto));
  }

  @GetMapping("/cart/{userId}")
  public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
  }

  @GetMapping("/coupon/{userId}/{code}")
  public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
    try {
      return ResponseEntity.ok(cartService.applyCoupon(userId, code));
    } catch (ValidationException validationException) {
      return ResponseEntity.badRequest().body(validationException.getMessage());
    }
  }

  @PostMapping("/increase")
  public ResponseEntity<OrderDto> increaseProductQuantity(
      @RequestBody AddProductInCartDto addProductInCartDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.increaseProductQuantity(addProductInCartDto));
  }

  @PostMapping("/decrease")
  public ResponseEntity<OrderDto> decreaseProductQuantity(
      @RequestBody AddProductInCartDto addProductInCartDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.decreaseProductQuantity(addProductInCartDto));
  }

  @PostMapping("/place-order")
  public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
  }
}

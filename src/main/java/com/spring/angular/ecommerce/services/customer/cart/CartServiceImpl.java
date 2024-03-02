package com.spring.angular.ecommerce.services.customer.cart;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.CartItemsDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.entities.CartItems;
import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.enums.OrderStatus;
import com.spring.angular.ecommerce.repositories.CartItemRepository;
import com.spring.angular.ecommerce.repositories.OrderRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import com.spring.angular.ecommerce.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** The type Cart service. */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;

  public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
    Order activeOrder =
        orderRepository.findByUserIdAndOrderStatus(
            addProductInCartDto.getUserId(), OrderStatus.Pending);
    Optional<CartItems> optionalCartItems =
        cartItemRepository.findByProductIdAndOrderIdAndUserId(
            addProductInCartDto.getProductId(),
            activeOrder.getId(),
            addProductInCartDto.getUserId());

    if (optionalCartItems.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    } else {
      Optional<Product> optionalProduct =
          productRepository.findById(addProductInCartDto.getProductId());
      Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

      if (optionalProduct.isPresent() && optionalUser.isPresent()) {
        CartItems cartItems =
            CartItems.builder()
                .product(optionalProduct.get())
                .price(optionalProduct.get().getPrice())
                .quantity(1L)
                .user(optionalUser.get())
                .order(activeOrder)
                .build();
        cartItemRepository.save(cartItems);
        activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
        activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
        activeOrder.getCartItems().add(cartItems);
        orderRepository.save(activeOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
      }
    }
  }

  public OrderDto getCartByUserId(Long userId) {
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
    List<CartItemsDto> cartItemsDtos =
        activeOrder.getCartItems().stream().map(CartItems::getCartDto).toList();
    return OrderDto.builder()
        .amount(activeOrder.getAmount())
        .totalAmount(activeOrder.getTotalAmount())
        .id(activeOrder.getId())
        .orderStatus(activeOrder.getOrderStatus())
        .discount(activeOrder.getDiscount())
        .cartItems(cartItemsDtos)
        .build();
  }
}

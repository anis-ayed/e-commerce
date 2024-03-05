package com.spring.angular.ecommerce.services.customer.cart;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.CartItemsDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.dto.PlaceOrderDto;
import com.spring.angular.ecommerce.entities.*;
import com.spring.angular.ecommerce.enums.OrderStatus;
import com.spring.angular.ecommerce.exceptions.ValidationException;
import com.spring.angular.ecommerce.repositories.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
  private final CouponRepository couponRepository;

  public ResponseEntity<?> addProductToCart(AddProductInCartDto productDto) {
    Order activeOrder =
        orderRepository.findByUserIdAndOrderStatus(productDto.getUserId(), OrderStatus.Pending);
    Optional<CartItems> existingCartItem =
        cartItemRepository.findByProductIdAndOrderIdAndUserId(
            productDto.getProductId(), activeOrder.getId(), productDto.getUserId());

    if (existingCartItem.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    } else {
      Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
      Optional<User> optionalUser = userRepository.findById(productDto.getUserId());

      if (optionalProduct.isPresent() && optionalUser.isPresent()) {
        CartItems cartItem =
            CartItems.builder()
                .product(optionalProduct.get())
                .price(optionalProduct.get().getPrice())
                .quantity(1L)
                .user(optionalUser.get())
                .order(activeOrder)
                .build();

        cartItemRepository.save(cartItem);
        updateOrderAndSave(activeOrder, cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem.getCartDto());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found!");
      }
    }
  }

  private void updateOrderAndSave(Order activeOrder, CartItems cartItems) {
    activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
    activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
    cartItemRepository.flush(); // Flush the changes to make sure we have the ID of the cartItem
    activeOrder
        .getCartItems()
        .add(
            cartItemRepository
                .findById(cartItems.getId())
                .orElseThrow(() -> new ValidationException("Add cartItems failed!")));
    orderRepository.save(activeOrder);
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
        .couponName((activeOrder.getCoupon() != null ? activeOrder.getCoupon().getName() : null))
        .build();
  }

  public OrderDto applyCoupon(Long userId, String code) {
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
    Coupon coupon =
        couponRepository
            .findByCode(code)
            .orElseThrow(() -> new ValidationException("Coupon code not found!"));

    if (couponIsExpired(coupon)) {
      throw new ValidationException("Coupon has expired");
    }

    double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
    double netAmount = activeOrder.getTotalAmount() - discountAmount;
    activeOrder.setAmount(netAmount);
    activeOrder.setDiscount(discountAmount);
    activeOrder.setCoupon(coupon);
    orderRepository.save(activeOrder);
    return activeOrder.getOrderDto();
  }

  private boolean couponIsExpired(Coupon coupon) {
    Date cuurentDate = new Date();
    Date expirationDate = coupon.getExpirationDate();
    return expirationDate != null && cuurentDate.after(expirationDate);
  }

  public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
    Order activeOrder =
        orderRepository.findByUserIdAndOrderStatus(
            addProductInCartDto.getUserId(), OrderStatus.Pending);

    Optional<Product> optionalProduct =
        productRepository.findById(addProductInCartDto.getProductId());

    Optional<CartItems> optionalCartItems =
        cartItemRepository.findByProductIdAndOrderIdAndUserId(
            addProductInCartDto.getProductId(),
            activeOrder.getId(),
            addProductInCartDto.getUserId());
    if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
      CartItems cartItems = optionalCartItems.get();
      Product product = optionalProduct.get();

      activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
      activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
      cartItems.setQuantity(cartItems.getQuantity() + 1);
      if (activeOrder.getCoupon() != null) {
        double discountAmount =
            ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount(netAmount);
        activeOrder.setDiscount(discountAmount);
      }

      cartItemRepository.save(cartItems);
      orderRepository.save(activeOrder);
      return activeOrder.getOrderDto();
    }
    return null;
  }

  public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
    Order activeOrder =
        orderRepository.findByUserIdAndOrderStatus(
            addProductInCartDto.getUserId(), OrderStatus.Pending);

    Optional<Product> optionalProduct =
        productRepository.findById(addProductInCartDto.getProductId());

    Optional<CartItems> optionalCartItems =
        cartItemRepository.findByProductIdAndOrderIdAndUserId(
            addProductInCartDto.getProductId(),
            activeOrder.getId(),
            addProductInCartDto.getUserId());
    if (optionalProduct.isPresent() && optionalCartItems.isPresent()) {
      CartItems cartItems = optionalCartItems.get();
      Product product = optionalProduct.get();

      activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
      activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());
      cartItems.setQuantity(cartItems.getQuantity() - 1);
      if (activeOrder.getCoupon() != null) {
        double discountAmount =
            ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount(netAmount);
        activeOrder.setDiscount(discountAmount);
      }

      cartItemRepository.save(cartItems);
      orderRepository.save(activeOrder);
      return activeOrder.getOrderDto();
    }
    return null;
  }

  public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
    Order activeOrder =
        orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
    Optional<User> user = userRepository.findById(placeOrderDto.getUserId());
    if (user.isPresent()) {
      activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
      activeOrder.setAddress(placeOrderDto.getAddress());
      activeOrder.setDate(new Date());
      activeOrder.setOrderStatus(OrderStatus.Placed);
      activeOrder.setTrackingId(UUID.randomUUID());
      orderRepository.save(activeOrder);

      Order order =
          Order.builder()
              .amount(0L)
              .totalAmount(0L)
              .discount(0L)
              .user(user.get())
              .orderStatus(OrderStatus.Pending)
              .build();
      orderRepository.save(order);
      return activeOrder.getOrderDto();
    }
    return null;
  }
}

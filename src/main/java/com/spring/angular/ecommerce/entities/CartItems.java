package com.spring.angular.ecommerce.entities;

import com.spring.angular.ecommerce.dto.CartItemsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long price;
  private Long quantity;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Order order;

  public CartItemsDto getCartDto() {
    return CartItemsDto.builder()
        .id(id)
        .price(price)
        .productId(product.getId())
        .quantity(quantity)
        .userId(user.getId())
        .productName(product.getName())
        .returnedImg(product.getImg())
        .build();
  }
}

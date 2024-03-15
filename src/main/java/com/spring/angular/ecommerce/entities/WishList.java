package com.spring.angular.ecommerce.entities;

import com.spring.angular.ecommerce.dto.WishListDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  public WishListDto getWishListDto() {
    return WishListDto.builder()
        .id(id)
        .price(product.getPrice())
        .userId(user.getId())
        .returnedImg(product.getImg())
        .productDescription(product.getDescription())
        .productId(product.getId())
        .productName(product.getName())
        .build();
  }
}

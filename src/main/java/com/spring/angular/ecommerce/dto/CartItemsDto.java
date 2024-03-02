package com.spring.angular.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDto {
  private Long id;
  private Long userId;
  private Long price;
  private Long quantity;
  private Long productId;
  private Long orderId;
  private String productName;
  private byte[] returnedImg;
}

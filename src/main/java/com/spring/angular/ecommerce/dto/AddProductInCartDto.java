package com.spring.angular.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddProductInCartDto {
  private Long userId;
  private Long productId;
}

package com.spring.angular.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListDto {
  private Long id;
  private Long productId;
  private Long userId;
  private String productName;
  private String productDescription;
  private byte[] returnedImg;
  private Long price;
}

package com.spring.angular.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderDto {
  private Long userId;
  private String address;
  private String orderDescription;
}

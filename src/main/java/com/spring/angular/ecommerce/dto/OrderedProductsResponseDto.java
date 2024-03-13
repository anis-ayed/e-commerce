package com.spring.angular.ecommerce.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderedProductsResponseDto {
  private List<ProductDto> productDtoList;
  private double orderAmount;
}

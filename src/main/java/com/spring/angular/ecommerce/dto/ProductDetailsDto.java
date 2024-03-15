package com.spring.angular.ecommerce.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsDto {
  private ProductDto productDto;
  private List<ReviewDto> reviewDtoList;
  private List<FAQDto> faqDtoList;
}

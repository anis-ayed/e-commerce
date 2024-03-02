package com.spring.angular.ecommerce.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
  private Long id;
  private String name;
  private String code;
  private Long discount;
  private Date expirationDate;
}

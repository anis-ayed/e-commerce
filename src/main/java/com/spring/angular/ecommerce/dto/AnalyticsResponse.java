package com.spring.angular.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnalyticsResponse {
  private Long placed;
  private Long shipped;
  private Long delivered;
  private Long currentMonthOrders;
  private Long previousMonthOrders;
  private double currentMonthEarnings;
  private double previousMonthEarnings;
}

package com.spring.angular.ecommerce.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private String source;
  private String message;
}

package com.spring.angular.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpErrorResponse {
    private String message;
}

package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.AnalyticsResponse;
import com.spring.angular.ecommerce.services.admin.analytics.OrdersAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {
  private final OrdersAnalyticsService ordersAnalyticsService;

  @GetMapping("/orders/analytics")
  public ResponseEntity<AnalyticsResponse> getAnalytics() {
    return ResponseEntity.ok(ordersAnalyticsService.calculateAnalytics());
  }
}

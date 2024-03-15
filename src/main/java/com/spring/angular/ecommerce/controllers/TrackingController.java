package com.spring.angular.ecommerce.controllers;

import com.spring.angular.ecommerce.services.customer.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/track")
public class TrackingController {
  private final CartService cartService;

  /**
   * Retrieves an order by its tracking ID.
   *
   * @param trackingId The unique tracking ID of the order.
   * @return ResponseEntity with the found OrderDto if the order exists, or
   *     ResponseEntity.notFound() if not found.
   */
  @Operation(
      summary = "Search Order by Tracking ID",
      description = "Retrieves an order by its tracking ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
      })
  @GetMapping("/{trackingId}")
  public ResponseEntity<?> searchOrderByTrackingId(@PathVariable UUID trackingId) {
    return ResponseEntity.ok(cartService.searchOrderByTrackingId(trackingId));
  }
}

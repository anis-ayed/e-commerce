package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.services.admin.order.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
  private final AdminOrderService adminOrderService;

  /**
   * Retrieves a list of all placed orders.
   *
   * @return ResponseEntity<List<OrderDto>> A ResponseEntity containing a list of OrderDto
   *     representing the placed orders.
   */
  @Operation(
      summary = "Get all placed orders",
      description = "Retrieves a list of all placed orders.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of placed orders"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/placed-orders")
  public ResponseEntity<List<OrderDto>> getAllPlacedOrders() {
    return ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
  }

  /**
   * Changes the status of an order to "Shipped" or "Delivered".
   *
   * @param orderId The ID of the order to be updated.
   * @param status The new status for the order. Accepted values: "Shipped" or "Delivered".
   * @return OrderDto The updated OrderDto with the new status.
   */
  @Operation(
      summary = "Change order status",
      description = "Changes the status of an order to 'Shipped' or 'Delivered'.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Order status changed successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @GetMapping("/order/{orderId}/{status}")
  public ResponseEntity<OrderDto> changeOrderStatus(
      @PathVariable Long orderId, @PathVariable String status) {
    return ResponseEntity.ok(adminOrderService.changeOrderStatus(orderId, status));
  }
}

package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.OrderedProductsResponseDto;
import com.spring.angular.ecommerce.dto.ReviewDto;
import com.spring.angular.ecommerce.services.customer.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Reviews customer", description = "Endpoints for customer reviews")
public class ReviewController {
  private final ReviewService reviewService;

  /**
   * Retrieves details of ordered products by order ID.
   *
   * @param orderId The ID of the order for which product details are requested.
   * @return ResponseEntity containing OrderedProductsResponseDto with details of ordered products.
   * @throws IllegalArgumentException if the order with the given ID is not found.
   */
  @GetMapping("/ordered-product/{orderId}")
  @Operation(
      summary = "Get ordered product details by order ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved ordered product details",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderedProductsResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  public ResponseEntity<OrderedProductsResponseDto> getOrderedProductDetailsByOrderId(
      @PathVariable Long orderId) {
    return ResponseEntity.ok(reviewService.getOrderedProductsResponseDto(orderId));
  }

  /**
   * Sets a review for a product.
   *
   * @param reviewDto The ReviewDto containing review details.
   * @return ResponseEntity containing ReviewDto with the created review details.
   * @throws IOException Thrown if there is an issue with input/output operations.
   * @throws IllegalArgumentException if the product with the given ID is not found.
   * @throws IllegalArgumentException if the user with the given ID is not found.
   */
  @PostMapping("/review")
  @Operation(
      summary = "Set a review for a product",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Review successfully created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReviewDto.class))),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  public ResponseEntity<ReviewDto> setReview(@ModelAttribute ReviewDto reviewDto)
      throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.setReview(reviewDto));
  }
}

package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.entities.Coupon;
import com.spring.angular.ecommerce.exceptions.ValidationException;
import com.spring.angular.ecommerce.services.admin.coupon.AdminCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {
  private final AdminCouponService adminCouponService;

  /**
   * Creates a new coupon using the provided coupon details.
   *
   * @param coupon The {@link Coupon} object containing the details of the coupon.
   * @return A {@link ResponseEntity} with status {@code 201 Created} and the created coupon in the
   *     response body. If validation fails, returns a {@code 400 Bad Request} status with the
   *     validation error message.
   */
  @Operation(summary = "Create a new coupon", description = "Endpoint to create a new coupon.")
  @ApiResponse(
      responseCode = "201",
      description = "Coupon created successfully",
      content =
          @Content(mediaType = "application/json", schema = @Schema(implementation = Coupon.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad Request - Validation error",
      content =
          @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
  @PostMapping
  public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(adminCouponService.createCoupon(coupon));
    } catch (ValidationException validationException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
    }
  }

  /**
   * Retrieves a list of all coupons.
   *
   * @return A {@link ResponseEntity} with status {@code 200 OK} and a list of coupons in the
   *     response body.
   */
  @Operation(
      summary = "Get all coupons",
      description = "Endpoint to retrieve a list of all coupons.")
  @ApiResponse(
      responseCode = "200",
      description = "OK - List of coupons",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = Coupon.class))))
  @GetMapping
  public ResponseEntity<List<Coupon>> getAllCoupons() {
    return ResponseEntity.ok(adminCouponService.getAllCoupons());
  }
}

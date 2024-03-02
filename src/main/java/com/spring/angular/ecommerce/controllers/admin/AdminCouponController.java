package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.entities.Coupon;
import com.spring.angular.ecommerce.exceptions.ValidationException;
import com.spring.angular.ecommerce.services.admin.AdminCouponService;
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

  @PostMapping
  public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(adminCouponService.createCoupon(coupon));
    } catch (ValidationException validationException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<Coupon>> getAllCoupons() {
    return ResponseEntity.ok(adminCouponService.getAllCoupons());
  }
}

package com.spring.angular.ecommerce.services.admin.coupon;

import com.spring.angular.ecommerce.entities.Coupon;
import java.util.List;

public interface AdminCouponService {
  public Coupon createCoupon(Coupon coupon);

  List<Coupon> getAllCoupons();
}

package com.spring.angular.ecommerce.services.admin.coupon;

import com.spring.angular.ecommerce.entities.Coupon;
import com.spring.angular.ecommerce.exceptions.ValidationException;
import com.spring.angular.ecommerce.repositories.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {
  private final CouponRepository couponRepository;

  public Coupon createCoupon(Coupon coupon) {
    if (couponRepository.existsByCode(coupon.getCode())) {
      throw new ValidationException("Coupon code already exists.");
    }
    return couponRepository.save(coupon);
  }

  public List<Coupon> getAllCoupons() {
    return couponRepository.findAll();
  }
}

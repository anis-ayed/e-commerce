package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

  boolean existsByCode(String code);
}

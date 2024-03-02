package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}

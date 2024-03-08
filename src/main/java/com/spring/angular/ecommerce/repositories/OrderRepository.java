package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.enums.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

  List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

  List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);
}

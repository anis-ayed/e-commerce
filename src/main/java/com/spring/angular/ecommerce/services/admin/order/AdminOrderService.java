package com.spring.angular.ecommerce.services.admin.order;

import com.spring.angular.ecommerce.dto.OrderDto;
import java.util.List;

public interface AdminOrderService {
  List<OrderDto> getAllPlacedOrders();

  OrderDto changeOrderStatus(Long orderId, String status);
}

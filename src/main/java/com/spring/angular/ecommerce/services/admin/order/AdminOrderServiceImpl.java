package com.spring.angular.ecommerce.services.admin.order;

import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.enums.OrderStatus;
import com.spring.angular.ecommerce.repositories.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminOrderServiceImpl implements AdminOrderService {
  private final OrderRepository orderRepository;

  public List<OrderDto> getAllPlacedOrders() {
    List<Order> orders =
        orderRepository.findAllByOrderStatusIn(
            List.of(OrderStatus.Placed, OrderStatus.Delivered, OrderStatus.Shipped));
    return orders.stream().map(Order::getOrderDto).toList();
  }

  public OrderDto changeOrderStatus(Long orderId, String status) {
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    if (optionalOrder.isEmpty()) {
      throw new IllegalArgumentException("Unknown order id: " + orderId);
    }

    Order order = optionalOrder.get();

    switch (status) {
      case "Shipped":
        order.setOrderStatus(OrderStatus.Shipped);
        break;
      case "Delivered":
        order.setOrderStatus(OrderStatus.Delivered);
        break;
      default:
        throw new IllegalArgumentException("Unknown order status: " + status);
    }
    return orderRepository.save(order).getOrderDto();
  }
}

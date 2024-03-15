package com.spring.angular.ecommerce.services.admin.analytics;

import com.spring.angular.ecommerce.dto.AnalyticsResponse;
import com.spring.angular.ecommerce.entities.Order;
import com.spring.angular.ecommerce.enums.OrderStatus;
import com.spring.angular.ecommerce.repositories.OrderRepository;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrdersAnalyticsServiceImpl implements OrdersAnalyticsService {
  private final OrderRepository orderRepository;

  public AnalyticsResponse calculateAnalytics() {
    LocalDate currentDate = LocalDate.now();
    LocalDate previousMonthDate = currentDate.minusMonths(1);

    Long currentMonthOrders =
        getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());

    Long previousMonthOrders =
        getTotalOrdersForMonth(previousMonthDate.getMonthValue(), currentDate.getYear());
    double currentMonthEarnings =
        getTotalMonthEarnings(currentDate.getMonthValue(), currentDate.getYear());
    double previousMonthEarnings =
        getTotalMonthEarnings(previousMonthDate.getMonthValue(), currentDate.getYear());

    Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
    Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);
    Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);

    return AnalyticsResponse.builder()
        .currentMonthEarnings(currentMonthEarnings)
        .previousMonthEarnings(previousMonthEarnings)
        .currentMonthOrders(currentMonthOrders)
        .previousMonthOrders(previousMonthOrders)
        .delivered(delivered)
        .shipped(shipped)
        .placed(placed)
        .build();
  }

  private double getTotalMonthEarnings(int month, int year) {
    Date startOfMonth = getStartOfMonth(month, year);
    Date endOfMonth = getEndOfMonth(month, year);
    List<Order> orderList = getOrdersBetweenDateAndOrderStatus(startOfMonth, endOfMonth);
    return orderList.stream().mapToDouble(Order::getAmount).sum();
  }

  private Long getTotalOrdersForMonth(int month, int year) {
    Date startOfMonth = getStartOfMonth(month, year);
    Date endOfMonth = getEndOfMonth(month, year);

    List<Order> orderList = getOrdersBetweenDateAndOrderStatus(startOfMonth, endOfMonth);
    return (long) orderList.size();
  }

  private Date getStartOfMonth(int month, int year) {
    Calendar calendar = getCalendar(month, year);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  private Date getEndOfMonth(int month, int year) {
    Calendar calendar = getCalendar(month, year);
    // Move the calendar to the end of the specified month
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);

    return calendar.getTime();
  }

  private Calendar getCalendar(int month, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    return calendar;
  }

  List<Order> getOrdersBetweenDateAndOrderStatus(Date startOfMonth, Date endOfMonth) {
    return orderRepository.findByDateBetweenAndOrderStatus(
        startOfMonth, endOfMonth, OrderStatus.Delivered);
  }
}

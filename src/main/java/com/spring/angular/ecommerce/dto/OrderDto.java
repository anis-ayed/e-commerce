package com.spring.angular.ecommerce.dto;

import com.spring.angular.ecommerce.enums.OrderStatus;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
  private Long id;

  private String orderDescription;
  private Date date;
  private double amount;
  private String address;
  private String payment;
  private OrderStatus orderStatus;
  private double totalAmount;
  private double discount;
  private UUID trackingId;
  private String userName;
  private String couponName;
  private List<CartItemsDto> cartItems;
}

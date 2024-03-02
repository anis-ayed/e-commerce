package com.spring.angular.ecommerce.entities;

import com.spring.angular.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderDescription;
  private Date date;
  private Long amount;
  private String address;
  private String payment;
  private OrderStatus orderStatus;
  private Long totalAmount;
  private Long discount;
  private UUID trackingId;

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
  @ToString.Exclude
  private List<CartItems> cartItems;
}

package com.spring.angular.ecommerce.entities;

import com.spring.angular.ecommerce.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long rating;
  @Lob private String description;

  @Column(columnDefinition = "longblob")
  @Lob
  private byte[] img;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Product product;

  public ReviewDto getReviewDto() {
    return ReviewDto.builder()
        .id(id)
        .rating(rating)
        .description(description)
        .returnedImg(img)
        .userId(user.getId())
        .productId(product.getId())
        .username(user.getName())
        .build();
  }
}

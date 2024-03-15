package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findAllByProductId(Long productId);
}

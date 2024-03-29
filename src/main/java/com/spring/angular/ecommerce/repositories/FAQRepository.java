package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.FAQ;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
  List<FAQ> findAllByProductId(Long productId);
}

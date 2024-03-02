package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByNameContaining(String productName);
}

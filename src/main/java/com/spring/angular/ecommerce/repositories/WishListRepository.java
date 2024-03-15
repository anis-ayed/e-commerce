package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.WishList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
  List<WishList> findAllByUserId(Long userId);
}

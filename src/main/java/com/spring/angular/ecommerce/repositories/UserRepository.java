package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.enums.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByEmail(String email);

  User findByRole(UserRole userRole);
}

package com.spring.angular.ecommerce.repositories;

import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    User findByRole(UserRole userRole);
}

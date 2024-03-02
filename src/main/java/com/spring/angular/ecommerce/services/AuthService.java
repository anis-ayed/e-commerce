package com.spring.angular.ecommerce.services;

import com.spring.angular.ecommerce.dto.SignupRequest;
import com.spring.angular.ecommerce.dto.UserDto;

public interface AuthService {
  UserDto createUser(SignupRequest signupRequest);

  Boolean hasUserWithEmail(String email);
}

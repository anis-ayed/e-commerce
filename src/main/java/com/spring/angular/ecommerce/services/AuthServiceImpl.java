package com.spring.angular.ecommerce.services;

import com.spring.angular.ecommerce.dto.SignupRequest;
import com.spring.angular.ecommerce.dto.UserDto;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.enums.UserRole;
import com.spring.angular.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}

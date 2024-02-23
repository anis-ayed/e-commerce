package com.spring.angular.ecommerce.services;

import com.spring.angular.ecommerce.dto.SignupRequest;
import com.spring.angular.ecommerce.dto.UserDto;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.enums.UserRole;
import com.spring.angular.ecommerce.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;


    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(user);

        return UserDto
                .builder()
                .id(createdUser.getId())
                .userRole(createdUser.getRole())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .build();
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userRepository.save(user);
        }
    }
}

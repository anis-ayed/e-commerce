package com.spring.angular.ecommerce.dto;

import com.spring.angular.ecommerce.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;

}

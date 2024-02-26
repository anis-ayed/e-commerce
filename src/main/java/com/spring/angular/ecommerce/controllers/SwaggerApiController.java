package com.spring.angular.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerApiController {
    @RequestMapping("/")
    public String redirect() {
        return "redirect:/swagger-ui/index.html";
    }
}

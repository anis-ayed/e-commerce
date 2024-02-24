package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.services.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(
            @ModelAttribute ProductDto productDto) throws IOException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addProduct(productDto));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}

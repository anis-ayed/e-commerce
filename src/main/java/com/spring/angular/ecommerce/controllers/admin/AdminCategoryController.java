package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.CategoryDto;
import com.spring.angular.ecommerce.entities.Category;
import com.spring.angular.ecommerce.services.admin.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(
            @RequestBody CategoryDto categoryDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}

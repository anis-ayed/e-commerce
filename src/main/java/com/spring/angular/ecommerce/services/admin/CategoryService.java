package com.spring.angular.ecommerce.services.admin;

import com.spring.angular.ecommerce.dto.CategoryDto;
import com.spring.angular.ecommerce.entities.Category;
import java.util.List;

public interface CategoryService {

  Category createCategory(CategoryDto categoryDto);

  List<Category> getAllCategories();
}

package com.spring.angular.ecommerce.services.admin.category;

import com.spring.angular.ecommerce.dto.CategoryDto;
import com.spring.angular.ecommerce.entities.Category;
import java.util.List;

public interface CategoryService {

  Category createCategory(CategoryDto categoryDto);

  List<Category> getAllCategories();
}

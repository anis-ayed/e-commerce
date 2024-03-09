package com.spring.angular.ecommerce.services.customer.product;

import com.spring.angular.ecommerce.dto.ProductDto;
import java.util.List;

public interface CustomerProductService {
  List<ProductDto> getAllProducts();

  List<ProductDto> getAllProductsByName(String productName);
}

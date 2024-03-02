package com.spring.angular.ecommerce.services.admin;

import com.spring.angular.ecommerce.dto.ProductDto;
import java.io.IOException;
import java.util.List;

public interface AdminProductService {
  ProductDto addProduct(ProductDto productDto) throws IOException;

  List<ProductDto> getAllProducts();

  List<ProductDto> getAllProductsByName(String productName);

  boolean deleteProductById(Long id);
}

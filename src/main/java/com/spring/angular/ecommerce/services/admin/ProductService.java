package com.spring.angular.ecommerce.services.admin;

import com.spring.angular.ecommerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();
}

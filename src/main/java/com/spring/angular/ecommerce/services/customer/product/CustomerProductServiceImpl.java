package com.spring.angular.ecommerce.services.customer.product;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {
  private final ProductRepository productRepository;

  public List<ProductDto> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }

  public List<ProductDto> getAllProductsByName(String productName) {
    List<Product> products = productRepository.findAllByNameContaining(productName);
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }
}

package com.spring.angular.ecommerce.services.admin.product;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.entities.Category;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.repositories.CategoryRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductDto addProduct(ProductDto productDto) throws IOException {
    Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
    Product product =
        Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .description(productDto.getDescription())
            .category(category)
            .img(productDto.getImg().getBytes())
            .build();
    return productRepository.save(product).getDto();
  }

  public List<ProductDto> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }

  public List<ProductDto> getAllProductsByName(String productName) {
    List<Product> products = productRepository.findAllByNameContaining(productName);
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }

  public boolean deleteProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public ProductDto getProductById(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Product not found with id : " + productId))
        .getDto();
  }

  public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Product not found with productId : " + productId));
    Category category =
        categoryRepository
            .findById(productDto.getCategoryId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Category not found with categoryId : " + productDto.getCategoryId()));
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setDescription(productDto.getDescription());
    product.setCategory(category);
    if (productDto.getImg() != null) {
      product.setImg(productDto.getImg().getBytes());
    }
    return productRepository.save(product).getDto();
  }
}

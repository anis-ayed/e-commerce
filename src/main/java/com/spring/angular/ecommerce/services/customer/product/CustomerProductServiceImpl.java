package com.spring.angular.ecommerce.services.customer.product;

import com.spring.angular.ecommerce.dto.ProductDetailsDto;
import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.entities.FAQ;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.entities.Review;
import com.spring.angular.ecommerce.repositories.FAQRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import com.spring.angular.ecommerce.repositories.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {
  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final FAQRepository faqRepository;

  public List<ProductDto> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }

  public List<ProductDto> getAllProductsByName(String productName) {
    List<Product> products = productRepository.findAllByNameContaining(productName);
    return products.stream().map(Product::getDto).collect(Collectors.toList());
  }

  public ProductDetailsDto getProductDetailsById(Long productId) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + productId));
    List<FAQ> faqs = faqRepository.findAllByProductId(productId);
    List<Review> reviews = reviewRepository.findAllByProductId(productId);
    return ProductDetailsDto.builder()
        .productDto(product.getDto())
        .faqDtoList(faqs.stream().map(FAQ::getFAQDto).toList())
        .reviewDtoList(reviews.stream().map(Review::getReviewDto).toList())
        .build();
  }
}

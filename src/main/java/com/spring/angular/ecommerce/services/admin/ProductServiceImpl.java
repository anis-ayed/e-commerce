package com.spring.angular.ecommerce.services.admin;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.entities.Category;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.repositories.CategoryRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow();
        Product product = Product.builder()
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
}

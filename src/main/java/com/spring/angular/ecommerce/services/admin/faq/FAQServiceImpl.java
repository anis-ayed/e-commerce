package com.spring.angular.ecommerce.services.admin.faq;

import com.spring.angular.ecommerce.dto.FAQDto;
import com.spring.angular.ecommerce.entities.FAQ;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.repositories.FAQRepository;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {
  private final FAQRepository faqRepository;
  private final ProductRepository productRepository;

  public FAQDto postFAQ(Long productId, FAQDto faqDto) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () -> new IllegalArgumentException("Product not found with ID: " + productId));
    FAQ faq =
        FAQ.builder()
            .question(faqDto.getQuestion())
            .answer(faqDto.getAnswer())
            .product(product)
            .build();
    return faqRepository.save(faq).getFAQDto();
  }
}

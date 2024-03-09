package com.spring.angular.ecommerce.services.admin.faq;

import com.spring.angular.ecommerce.dto.FAQDto;

public interface FAQService {
  FAQDto postFAQ(Long productId, FAQDto faqDto);
}

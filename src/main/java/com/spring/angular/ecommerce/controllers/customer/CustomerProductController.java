package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.services.customer.product.CustomerProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {
  private final CustomerProductService customerProductService;

  /**
   * Retrieves a list of all products.
   *
   * @return ResponseEntity containing a list of ProductDto and HTTP status code.
   */
  @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of products"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/products")
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    return ResponseEntity.ok(customerProductService.getAllProducts());
  }

  /**
   * Retrieves a list of products by searching for a specific product name.
   *
   * @param productName The name of the product to search for.
   * @return ResponseEntity containing a list of ProductDto and HTTP status code.
   */
  @Operation(
      summary = "Get products by name",
      description = "Retrieves a list of products by searching for a specific product name.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of products"),
        @ApiResponse(responseCode = "404", description = "Products not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/products/search/{productName}")
  public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String productName) {
    return ResponseEntity.ok(customerProductService.getAllProductsByName(productName));
  }
}

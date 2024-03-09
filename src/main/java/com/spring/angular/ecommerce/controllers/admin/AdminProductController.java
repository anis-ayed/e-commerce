package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.FAQDto;
import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.services.admin.faq.FAQService;
import com.spring.angular.ecommerce.services.admin.product.AdminProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Products")
public class AdminProductController {
  private final AdminProductService adminProductService;
  private final FAQService faqService;

  /**
   * Endpoint to add a new product.
   *
   * @param productDto The data transfer object (DTO) containing product information.
   * @return ResponseEntity containing the created ProductDto and HTTP status code.
   * @throws IOException If there is an issue handling the product data.
   */
  @Operation(summary = "Add a new product", description = "Endpoint to add a new product.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Product added successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping("/products")
  public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto)
      throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(adminProductService.addProduct(productDto));
  }

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
    return ResponseEntity.ok(adminProductService.getAllProducts());
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
    return ResponseEntity.ok(adminProductService.getAllProductsByName(productName));
  }

  /**
   * Deletes a product by its ID.
   *
   * @param idProduct The ID of the product to be deleted.
   * @return ResponseEntity indicating the result of the deletion operation.
   */
  @Operation(summary = "Delete a product by ID", description = "Deletes a product by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
      })
  @DeleteMapping("/products/{idProduct}")
  public ResponseEntity<Void> deleteProductById(@PathVariable Long idProduct) {
    boolean isDeleted = adminProductService.deleteProductById(idProduct);
    if (isDeleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Creates a new FAQ for a given product.
   *
   * @param productId The identifier of the product for which to create the FAQ. [PathVariable]
   * @param faqDto The FAQ details to be created. [RequestBody]
   * @return A `ResponseEntity` containing the created FAQ as a `FAQDto` object with a 201 Created
   *     status code.
   * @throws IllegalArgumentException if the product with the given ID is not found.
   */
  @PostMapping("/faq/{productId}")
  @Operation(
      summary = "Create FAQ for a Product",
      description = "Creates a new FAQ for a given product")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "FAQ created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid FAQ data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
      })
  public ResponseEntity<FAQDto> postFAQ(
      @Parameter(
              name = "productId",
              description = "The identifier of the product.",
              required = true)
          @PathVariable
          Long productId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The details of the FAQ to be created.",
              required = true)
          @RequestBody
          FAQDto faqDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAQ(productId, faqDto));
  }

  /**
   * Retrieves a product by its ID.
   *
   * @param productId the ID of the product to retrieve
   * @return the product details as a DTO, or a 404 Not Found response if the product is not found
   * @throws IllegalArgumentException if the product with the given ID is not found.
   */
  @Operation(summary = "Retrieve a product by ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product found"),
    @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/products/{productId}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
    return ResponseEntity.ok(adminProductService.getProductById(productId));
  }

  /**
   * Updates an existing product with new information.
   *
   * @param productId the ID of the product to update
   * @param productDto the updated product details in a DTO
   * @return the updated product details as a DTO
   * @throws IllegalArgumentException if the product with the given ID is not found.
   * @throws IllegalArgumentException if the category of product is not found.
   * @throws IOException if an error occurs while updating product information
   */
  @Operation(summary = "Update an existing product")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid product data"),
    @ApiResponse(responseCode = "404", description = "Product not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/products/{productId}")
  public ResponseEntity<ProductDto> updateProduct(
      @Parameter(name = "productId", description = "ID of the product to update", required = true)
          @PathVariable
          Long productId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Updated product information",
              required = true)
          @RequestBody
          ProductDto productDto)
      throws IOException {
    return ResponseEntity.ok(adminProductService.updateProduct(productId, productDto));
  }
}

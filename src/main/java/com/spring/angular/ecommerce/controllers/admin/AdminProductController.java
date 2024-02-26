package com.spring.angular.ecommerce.controllers.admin;

import com.spring.angular.ecommerce.dto.ProductDto;
import com.spring.angular.ecommerce.services.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;


    /**
     * Endpoint to add a new product.
     *
     * @param productDto The data transfer object (DTO) containing product information.
     * @return ResponseEntity containing the created ProductDto and HTTP status code.
     * @throws IOException If there is an issue handling the product data.
     */
    @Operation(summary = "Add a new product", description = "Endpoint to add a new product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(
            @ModelAttribute ProductDto productDto) throws IOException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addProduct(productDto));
    }


    /**
     * Retrieves a list of all products.
     *
     * @return ResponseEntity containing a list of ProductDto and HTTP status code.
     */
    @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    /**
     * Retrieves a list of products by searching for a specific product name.
     *
     * @param productName The name of the product to search for.
     * @return ResponseEntity containing a list of ProductDto and HTTP status code.
     */
    @Operation(summary = "Get products by name", description = "Retrieves a list of products by searching for a specific product name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products"),
            @ApiResponse(responseCode = "404", description = "Products not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/products/search/{productName}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String productName) {
        return ResponseEntity.ok(productService.getAllProductsByName(productName));
    }


    /**
     * Deletes a product by its ID.
     *
     * @param idProduct The ID of the product to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @Operation(summary = "Delete a product by ID", description = "Deletes a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/products/{idProduct}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long idProduct) {
        boolean isDeleted = productService.deleteProductById(idProduct);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

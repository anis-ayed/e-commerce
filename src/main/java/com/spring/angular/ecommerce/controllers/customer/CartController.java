package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.AddProductInCartDto;
import com.spring.angular.ecommerce.dto.OrderDto;
import com.spring.angular.ecommerce.dto.PlaceOrderDto;
import com.spring.angular.ecommerce.exceptions.ValidationException;
import com.spring.angular.ecommerce.services.customer.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  /**
   * Adds a product to the customer's shopping cart.
   *
   * @param addProductInCartDto The DTO containing information about the product to be added.
   * @return ResponseEntity<?> A ResponseEntity indicating the status of the operation.
   */
  @Operation(
      summary = "Add product to cart",
      description = "Adds a product to the customer's shopping cart.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Product added to cart successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @PostMapping("/cart")
  public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.addProductToCart(addProductInCartDto));
  }

  /**
   * Retrieves the customer's shopping cart by user ID.
   *
   * @param userId The ID of the user.
   * @return ResponseEntity<OrderDto> A ResponseEntity containing the customer's shopping cart
   *     information.
   */
  @Operation(
      summary = "Get cart by user ID",
      description = "Retrieves the customer's shopping cart by user ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of the customer's shopping cart"),
        @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @GetMapping("/cart/{userId}")
  public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
  }

  /**
   * Applies a coupon to the customer's shopping cart.
   *
   * @param userId The ID of the user.
   * @param code The coupon code to apply.
   * @return ResponseEntity<?> A ResponseEntity indicating the status of the operation.
   */
  @Operation(
      summary = "Apply coupon to cart",
      description = "Applies a coupon to the customer's shopping cart.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Coupon applied successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @GetMapping("/coupon/{userId}/{code}")
  public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
    try {
      return ResponseEntity.ok(cartService.applyCoupon(userId, code));
    } catch (ValidationException validationException) {
      return ResponseEntity.badRequest().body(validationException.getMessage());
    }
  }

  /**
   * Increases the quantity of a product in the customer's shopping cart.
   *
   * @param addProductInCartDto The DTO containing information about the product and quantity to be
   *     increased.
   * @return ResponseEntity<OrderDto> A ResponseEntity containing the updated customer's shopping
   *     cart.
   */
  @Operation(
      summary = "Increase product quantity",
      description = "Increases the quantity of a product in the customer's shopping cart.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Product quantity increased successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @PostMapping("/increase")
  public ResponseEntity<OrderDto> increaseProductQuantity(
      @RequestBody AddProductInCartDto addProductInCartDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.increaseProductQuantity(addProductInCartDto));
  }

  /**
   * Decreases the quantity of a product in the customer's shopping cart.
   *
   * @param addProductInCartDto The DTO containing information about the product and quantity to be
   *     decreased.
   * @return ResponseEntity<OrderDto> A ResponseEntity containing the updated customer's shopping
   *     cart.
   */
  @Operation(
      summary = "Decrease product quantity",
      description = "Decreases the quantity of a product in the customer's shopping cart.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Product quantity decreased successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @PostMapping("/decrease")
  public ResponseEntity<OrderDto> decreaseProductQuantity(
      @RequestBody AddProductInCartDto addProductInCartDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cartService.decreaseProductQuantity(addProductInCartDto));
  }

  /**
   * Places an order for the items in the customer's shopping cart.
   *
   * @param placeOrderDto The DTO containing information for placing the order.
   * @return ResponseEntity<OrderDto> A ResponseEntity containing the details of the placed order.
   */
  @Operation(
      summary = "Place order",
      description = "Places an order for the items in the customer's shopping cart.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Order placed successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = String.class)))
      })
  @PostMapping("/place-order")
  public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
  }

  /**
   * Retrieves a list of placed orders for a given user.
   *
   * @param userId The identifier of the user for whom to retrieve placed orders.
   * @return A `ResponseEntity` containing a list of `OrderDto` objects representing the user's
   *     placed orders. - Status code: 200 OK - The request was successful. - Status code: 404 Not
   *     Found - The user with the provided ID was not found.
   * @throws IllegalArgumentException if the provided `userId` is null.
   */
  @GetMapping("/my-orders/{userId}")
  public ResponseEntity<List<OrderDto>> getMyPlacedOrders(@PathVariable Long userId) {
    return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
  }
}

package com.spring.angular.ecommerce.controllers.customer;

import com.spring.angular.ecommerce.dto.WishListDto;
import com.spring.angular.ecommerce.services.customer.wishList.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class WishListController {
  private final WishListService wishListService;

  /**
   * Adds a product to the user's wish list.
   *
   * <p>This endpoint allows users to add a product to their wish list.
   *
   * @param wishListDto The WishListDto object containing the details of the product to be added to
   *     the wish list.
   * @return A ResponseEntity with the status code indicating the success or failure of the
   *     operation, along with the updated WishListDto representing the user's wish list after the
   *     addition of the product. If the product is successfully added, the HTTP status code
   *     returned is 201 (Created). If there are any errors or if the request is invalid, an
   *     appropriate error response is returned.
   * @throws IllegalArgumentException if the user with the given ID is not found.
   * @throws IllegalArgumentException if the product with the given ID is not found.
   */
  @Operation(
      summary = "Add a product to the wish list",
      description = "Allows users to add a product to their wish list")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Product successfully added to the wish list"),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid input provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping("/wish-list")
  public ResponseEntity<WishListDto> addProductToWishList(@RequestBody WishListDto wishListDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(wishListService.addProductToWishList(wishListDto));
  }

  /**
   * Retrieves all wish list items associated with a user.
   *
   * <p>This endpoint allows users to retrieve all items in their wish list based on their user ID.
   *
   * @param userId The ID of the user whose wish list items are to be retrieved.
   * @return A ResponseEntity with the status code indicating the success or failure of the
   *     operation, along with the list of WishListDto representing the user's wish list items. If
   *     the wish list items are successfully retrieved, the HTTP status code returned is 200 (OK).
   *     If the user does not have any wish list items or if there are any errors, an empty list is
   *     returned with the appropriate HTTP status code and error message.
   */
  @Operation(
      summary = "Get all wish list items by user ID",
      description = "Retrieves all wish list items associated with a user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Wish list items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found or wish list is empty"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/wish-list/{userId}")
  public ResponseEntity<List<WishListDto>> getAllWishListByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(wishListService.getAllWishListByUserId(userId));
  }
}

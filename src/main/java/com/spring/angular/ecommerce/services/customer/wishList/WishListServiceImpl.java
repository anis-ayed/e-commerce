package com.spring.angular.ecommerce.services.customer.wishList;

import com.spring.angular.ecommerce.dto.WishListDto;
import com.spring.angular.ecommerce.entities.Product;
import com.spring.angular.ecommerce.entities.User;
import com.spring.angular.ecommerce.entities.WishList;
import com.spring.angular.ecommerce.repositories.ProductRepository;
import com.spring.angular.ecommerce.repositories.UserRepository;
import com.spring.angular.ecommerce.repositories.WishListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final WishListRepository wishListRepository;

  public WishListDto addProductToWishList(WishListDto wishListDto) {
    Product product =
        productRepository
            .findById(wishListDto.getProductId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Product not found with id: " + wishListDto.getProductId()));

    User user =
        userRepository
            .findById(wishListDto.getUserId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "User not found with id: " + wishListDto.getUserId()));
    WishList wishList = WishList.builder().product(product).user(user).build();
    return wishListRepository.save(wishList).getWishListDto();
  }

  public List<WishListDto> getAllWishListByUserId(Long userId) {
    return wishListRepository.findAllByUserId(userId).stream()
        .map(WishList::getWishListDto)
        .toList();
  }
}

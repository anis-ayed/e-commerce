package com.spring.angular.ecommerce.services.customer.wishList;

import com.spring.angular.ecommerce.dto.WishListDto;
import java.util.List;

public interface WishListService {
  WishListDto addProductToWishList(WishListDto wishListDto);

  List<WishListDto> getAllWishListByUserId(Long userId);
}

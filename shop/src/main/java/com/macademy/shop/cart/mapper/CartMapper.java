package com.macademy.shop.cart.mapper;

import com.macademy.shop.cart.dto.CartDetailDto;
import com.macademy.shop.cart.dto.CartDto;
import com.macademy.shop.cart.dto.CartItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper {
    int insertCart(CartDto cartDto);

    int insertCartItem(CartItemDto cartItemDto);

    CartDto findMemberCart(Long memberId);

    CartItemDto findItemInCart(Map map);

    int updateCount(Map map);

    List<CartDetailDto> findCartDetail(Long cartId);

    Long findMemberId(Long cartItemId);

    CartItemDto findMemberId(Long cartItemId);

    int deleteCartItem(Long cartItemId);
}

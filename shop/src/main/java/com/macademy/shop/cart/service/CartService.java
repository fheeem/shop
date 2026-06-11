package com.macademy.shop.cart.service;

import com.macademy.shop.cart.dto.CartDetailDto;
import com.macademy.shop.cart.dto.CartDto;
import com.macademy.shop.cart.dto.CartItemDto;
import com.macademy.shop.cart.form.CartForm;
import com.macademy.shop.cart.mapper.CartMapper;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.mapper.ItemMapper;
import com.macademy.shop.member.mapper.MemberMapper;
import com.macademy.shop.order.form.OrderForm;
import com.macademy.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
//@Transactional
@RequiredArgsConstructor
public class CartService {

    private final OrderService orderService;
    private final ItemMapper itemMapper;
    private final MemberMapper memberMapper;
    private final CartMapper cartMapper;

    //장바구니에 상품 추가
    public Long addCart(CartForm cartForm, String id){
        Long memberId = memberMapper.findMemberId(id);

        if (memberId == null) {
            throw new IllegalArgumentException("아이디를 찾을수없습니다");
        }

        //카드가 존재하는지 확인
        CartDto cart = checkCart(memberId);

        return addItemToCart(cart, cartForm);
    }

    //장바구니가 존재하는지 확인
    private CartDto checkCart(Long memberId) {
        CartDto cart = cartMapper.findMemberCart(memberId);


        if(cart == null) {
            CartDto cartDto = new CartDto();
            cartDto.setMemberId(memberId);
            cartMapper.insertCart(cartDto);

            cart = cartMapper.findMemberCart(memberId);
        }

        return cart;
    }

    //장바구니에 상품조회하여 추가하거나 수량 증가
    private Long addItemToCart(CartDto cartDto, CartForm cartForm) {
        ItemDto itemDto = itemMapper.selectItem(cartForm.getItemId());

        Map<String, Object> map = new HashMap<>();
        map.put("cartId", cartDto.getCartId());
        map.put("itemId", itemDto.getItemId());

        CartItemDto cartItemDto = cartMapper.findItemInCart(map);

        if(cartItemDto == null) {
            cartItemDto = new CartItemDto();
            cartItemDto.setCartId(cartDto.getCartId());
            cartItemDto.setItemId(itemDto.getItemId());
            cartItemDto.setCount(cartForm.getCount());

            cartMapper.insertCartItem(cartItemDto);
        } else {
            cartItemDto.setCount(cartForm.getCount() + cartItemDto.getCount());
            map.put("count", cartItemDto.getCount());
            map.put("cartItemId", cartItemDto.getCartItemId());
            cartMapper.updateCount(map);
        }
        return cartItemDto.getCartId();
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String id) {
        Long memberId = memberMapper.findMemberId(id);

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        CartDto cart = cartMapper.findMemberCart(memberId);

        if (cart == null) {
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartMapper.findCartDetail(cart.getCartId());

        return cartDetailDtoList;
    }
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String id) {
        Long loginMemberId = memberMapper.findMemberId(id);

        Long curMemberId = memberMapper.findMemberId(cartItemId);

        if(loginMemberId != curMemberId)
            return false;

        return true;
    }

    @Transactional(readOnly = true)
    public void updateCartItemCount(Long cartItemId, int count){
        CartItemDto cartItemDto = cartMapper.findCartItem(cartItemId);

        if(cartItemDto == null) {
            throw new IllegalArgumentException("상품이 존재하지않는다");
        }

        Map map = new HashMap();
        map.put("count", count);
        map.put("cartItemId", cartItemId);

        cartMapper.updateCount(map);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItemDto cartItemDto = cartMapper.findCartItem(cartItemId);

        if(cartItemDto == null)
            throw new IllegalArgumentException("상품이 존재하지않습니다.");

        cartMapper.deleteCartItem(cartItemId);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String id) {
        List<OrderForm> orderFormList = new ArrayList<>();

        for(CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItemDto cartItemDto =
                        cartMapper.findCartItem(cartOrderDto);

            orderFormList.add(orderForm);
        }

        Long orderId =orderService.cartOrders(orderFormList, id);

        for(CartOrderDto cartOrderDto : CartOrderDtoList) {
            cartMapper.deleteCartItem(cartOrderDto.getCartItemId());
        }

        return orderId;
    }


}

package com.macademy.shop;

import com.macademy.shop.cart.dto.CartItemDto;
import com.macademy.shop.cart.form.CartForm;
import com.macademy.shop.cart.mapper.CartMapper;
import com.macademy.shop.cart.service.CartService;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.member.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest
public class CartServiceTest extends CommonSerivceTest{

    @Autowired
    private CartService cartService;

    @Autowired
    private CartMapper cartMapper;

    private MemberDto memberDto;
    private ItemDto itemDto;

    @BeforeEach
    void setUp(){
        memberDto = createMember();
        itemDto = createItem(100);
    }

    @Test
    public void testAddCart() {
        CartForm cartForm = new CartForm();
        cartForm.setItemId(itemDto.getItemId());
        cartForm.setCount(1);

        Long cartId = cartService.addCart(cartForm, memberDto.getId());
        assertThat(cartId).isNotNull();

        cartService.addCart(cartForm, memberDto.getId());

        Map<String,Object> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("itemid", itemDto.getItemId());

        CartItemDto cartItemDto = cartMapper.findItemInCart(map);
        assertThat(cartItemDto).isNotNull();
        assertThat(cartItemDto.getCount()).isEqualTo(2);
    }

}

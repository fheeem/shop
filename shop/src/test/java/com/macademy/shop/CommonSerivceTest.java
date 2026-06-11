package com.macademy.shop;

import com.macademy.shop.item.constant.ItemSellStatus;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.mapper.ItemMapper;
import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class CommonSerivceTest {
    @Autowired
    protected MemberMapper mapperMapper;

    @Autowired
    protected ItemMapper itemMapper;

    protected MemberDto createMember (){
        MemberDto memberDto = new MemberDto();

        memberDto.setId("test_" + System.nanoTime());
        memberDto.setEmail("test_" + System.nanoTime() + "@naver.com");
        memberDto.setPassword("1234");
        memberDto.setName("테스트회원");
        mapperMapper.insertMember(memberDto);
        return memberDto;
    }

    protected ItemDto createItem (int stock) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName("테스트상품");
        itemDto.setPrice(10000);
        itemDto.setStockNumber(stock);
        itemDto.setItemSellStatus(ItemSellStatus.SELL);
        itemMapper.itemInsert(itemDto);

        return itemDto;
    }
}

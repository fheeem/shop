package com.macademy.shop.order.service;

import com.macademy.shop.exception.OutofStockException;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.mapper.ItemMapper;
import com.macademy.shop.order.dto.OrderItemDto;
import com.macademy.shop.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    public OrderItemDto createOrderItem(Long orderId, Long itemId, int count) {
        OrderItemDto orderItemDto = new OrderItemDto();

        ItemDto itemDto = itemMapper.selectItem(itemId);

        orderItemDto.setItemId(itemId);
        orderItemDto.setCount(count);
        orderItemDto.setOrderPrice(this.getTotalPrice(itemDto.getPrice(), count));
        orderItemDto.setOrderId(orderId);

        this.removeStock(itemDto, count);


        System.out.println("orderItemService(stockNumber) : " + itemDto.getStockNumber());

        orderMapper.insertOrderItem(orderItemDto);

        return orderItemDto;
    }

    //재고감소
    public void removeStock(ItemDto itemDto, int stockNumber) {
        int restStock = itemDto.getStockNumber() - stockNumber;

        if(restStock < 0) {
            throw new OutofStockException(
                                "삼품의 재고가 부족합니다 . (현재 재고 수량 : "
                                + itemDto.getStockNumber() + ")"
            );
        }

        Map map = new HashMap();

        map.put("itemId", itemDto.getItemId());
        map.put("stockNumber", restStock);

        orderMapper.changeStock(map);
    }

    public void addStock(Long itemId, int stockNumber) {
        ItemDto itemDto = itemMapper.selectItem(itemId);

        int restStock = itemDto.getStockNumber() + stockNumber;

        Map map = new HashMap();

        map.put("itemid", itemDto.getItemId());
        map.put("stockNumber", restStock);

        orderMapper.changeStock(map);
    }


    //상품가격 개선
    public int getTotalPrice(int price, int count) {
        return price * count;
    }
}

package com.macademy.shop;

import com.macademy.shop.item.constant.ItemSellStatus;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.mapper.ItemMapper;
import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.mapper.MemberMapper;
import com.macademy.shop.order.constant.OrderStatus;
import com.macademy.shop.order.dto.OrderHistDto;
import com.macademy.shop.order.form.OrderForm;
import com.macademy.shop.order.mapper.OrderMapper;
import com.macademy.shop.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@Transactional
public class OrderServiceTest extends CommonSerivceTest{

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    @DisplayName("주문테스트")
    public void createOrderTest(){
        MemberDto member = createMember();
        ItemDto item = createItem(100);

        OrderForm orderForm = new OrderForm();
        orderForm.setItemId(item.getItemId());
        orderForm.setCount(2);

        Long orderId = orderService.createOrder(orderForm, member.getId());

        OrderHistDto order = orderMapper.findOrder(orderId);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);

        ItemDto updateItem = itemMapper.selectItem(item.getItemId());

    }

    @Test
    @DisplayName("주문취소시 재고 복구")
    public void cancelOrderTest(){
        MemberDto member = createMember();
        ItemDto item = createItem(100);

        OrderForm orderForm = new OrderForm();
        orderForm.setItemId(item.getItemId());
        orderForm.setCount(3);

        Long orderId = orderService.createOrder(orderForm, member.getId());

        orderService.cancelOrder(orderId);

        OrderHistDto order = orderMapper.findOrder(orderId);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);

        ItemDto updateItem = itemMapper.selectItem(item.getItemId());
        assertThat(updateItem.getStockNumber()).isEqualTo(100);
    }
}

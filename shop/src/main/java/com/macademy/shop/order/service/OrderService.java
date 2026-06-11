package com.macademy.shop.order.service;


import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.mapper.MemberMapper;
import com.macademy.shop.order.constant.OrderStatus;
import com.macademy.shop.order.dto.OrderDto;
import com.macademy.shop.order.dto.OrderHistDto;
import com.macademy.shop.order.form.OrderForm;
import com.macademy.shop.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final MemberMapper memberMapper;

    private final OrderItemService orderItemService;

    public Long createOrder(OrderForm orderForm, String id) {
        OrderDto orderDto = new OrderDto();

        MemberDto memberDto = memberMapper.loginMember(id);
        orderDto.setMemberId(memberDto.getMemberId());
        orderDto.setOrderStatus(OrderStatus.ORDER);

        orderMapper.insertOrder(orderDto);

        orderItemService.createOrderItem(orderDto.getOrderId(), orderForm.getItemId(), orderForm.getCount());
        System.out.println("orderService(orderDto) : " + orderDto);
        System.out.println("orderService(orderId) : " + orderDto.getOrderId());

        return orderDto.getOrderId();
    }

    public List<OrderHistDto> orderSelect(Map map){
        return orderMapper.orderSelect(map);
    }

    public int orderCount(Map map){
        return orderMapper.orderCount(map);
    }

    // 주문 취소
    public Long cancelOrder(Long orderId) {

        List<OrderHistDto> orderItems = orderMapper.findOrder(orderId);

        for( OrderHistDto item : orderItems) {
            orderItemService.addStock(item,getItemId(), item.getCount());
        }
        orderItemService.addStock(orderHistDto.getItemId(), orderHistDto.getCount());
        orderMapper.cancelOrder(orderId);

        return orderId;
    }

    //로그인한 사용자와 주문한 사용자가 같은 사람인지 확인
    public boolean validateOrder(Long orderId, String id) {
        Long loginId = Long.valueOf(memberMapper.findMemberId(id));

        Long memberId = orderMapper.orderMemberId(orderId);

        if(loginId != memberId) return false;

        return true;
    }

    public Long cartOrders(List<OrderForm> orderFormList, String id){
        MemberDto member = memberMapper.loginMember(id);

        OrderDto orderDto = new OrderDto();
        orderDto.setMemberId(member.getMemberId());
        orderDto.setOrderStatus(OrderStatus.ORDER);

        //주문 테이블에 추가 (실제 주문 처리는 여기임)
        orderMapper.insertOrder(orderDto);

        for(OrderForm orderForm : orderFormList) {
            orderItemService.createOrderItem(orderDto.getOrderId()
                                            , orderForm.getItemId()
                                            , orderForm.getCount());
        }

        return orderDto.getOrderId();
    }
}

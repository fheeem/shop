package com.macademy.shop.order.mapper;

import com.macademy.shop.order.dto.OrderDto;
import com.macademy.shop.order.dto.OrderHistDto;
import com.macademy.shop.order.dto.OrderItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    Long insertOrder(OrderDto orderDto);

    Long insertOrderItem(OrderItemDto orderItemDto);

    int changeStock(Map map);

    List<OrderHistDto> orderSelect(Map map);

    int orderCount(Map map);

    Long orderMemberId(Long orderId);

    int cancelOrder(Long orderId);

    OrderHistDto findOrder(Long orderId);
}

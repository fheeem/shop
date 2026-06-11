package com.macademy.shop.order.dto;

import com.macademy.shop.order.constant.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private Long orderId;

    private Long memberId;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}

package com.macademy.shop.order.dto;

import com.macademy.shop.order.constant.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHistDto {

    private Long orderId;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private Long itemId;

    private String itemName;

    private int orderPrice;

    private int count;

    private  String imgUrl;
}

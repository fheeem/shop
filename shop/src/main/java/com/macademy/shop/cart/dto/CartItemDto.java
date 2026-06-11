package com.macademy.shop.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long cartItemId;
    private Long cartId;
    private Long itemId;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}

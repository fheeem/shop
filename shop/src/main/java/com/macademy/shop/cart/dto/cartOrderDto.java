package com.macademy.shop.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class cartOrderDto {
    private Long cartItemId;
    
    private List<CartOrderDto> cartOrderDtoList;
    
}

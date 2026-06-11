package com.macademy.shop.main.dto;

import lombok.Data;

@Data
public class MainItemDto {
    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imgUrl;

    private Integer price;
}

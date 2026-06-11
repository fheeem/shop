package com.macademy.shop.cart.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartForm {
    @NotNull(message = "상품 아이디는 필수입니다")
    private Long itemId;

    @Min(value = 1, message = "최소주문수량은 1개입니다" )
    private int count;
}

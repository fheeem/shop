package com.macademy.shop.order.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderForm {

    @NotNull (message="상품아이디는 필수값입니다")
    private Long itemId;

    @Min(value = 1, message = "최소 주문수량은 1개입니다.")
    @Max(value = 100, message = "최대주문 수량은 100개입니다 ")
    private int count;
}

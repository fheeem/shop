package com.macademy.shop.item.form;

import com.macademy.shop.item.constant.ItemSellStatus;
import com.macademy.shop.item.dto.ItemImgDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemForm {

    private Long Id;

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세 설명은 필수 입력값입니다.")
    private String itemDetail;

    @NotNull(message = "재고수량은 필수 입력값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();
}

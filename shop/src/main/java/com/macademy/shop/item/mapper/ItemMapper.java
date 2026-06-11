package com.macademy.shop.item.mapper;

import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.dto.ItemImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    int itemInsert(ItemDto itemDto);

    int insertItemImg(ItemImgDto itemImgDto);

    List<ItemDto> itemListAll();


    ItemDto selectItem(Long itemId);

    List<ItemImgDto> selectItemImg(Long itemId);

    ItemImgDto selectItemImgId(Long itemImgId);

    int updateItem(ItemDto itemDto);

    int updateItemImg(ItemImgDto itemImgDto);

    List<ItemDto> itemListPage(Map map);

    int countAdminItems(Map map);
}

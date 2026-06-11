package com.macademy.shop.item.service;

import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.dto.ItemImgDto;
import com.macademy.shop.item.form.ItemForm;
import com.macademy.shop.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)   //모든 예외 발생시 롤백
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;

    private final ItemImgService itemImgService;


    public Long itemInsert(ItemForm itemForm, List<MultipartFile> itemImgFileList) throws Exception {
        //상품을 등록
        ItemDto itemDto = makeItemDto(itemForm);

        itemMapper.itemInsert(itemDto);

        //이미지를 등록
        for (int i=0; i<itemImgFileList.size(); i++) {
            ItemImgDto itemImgDto = new ItemImgDto();

            //상품id만 전달
            //나머지 이미지에 대한 정보는 FileService에서 설정
            itemImgDto.setItemId(itemDto.getItemId());

            //첫번째 이미지를 대표이미지로 설정
            if (i == 0) {
                itemImgDto.setRepImgYn("Y");
            } else {
                itemImgDto.setRepImgYn("N");
            }

            //상품이미지를 저장
            itemImgService.saveItemImg(itemImgDto, itemImgFileList.get(i));
        }

        return itemDto.getItemId();

    }

    public List<ItemDto> itemListAll() {
        return itemMapper.itemListAll();
    }

    // 등록된 상품 정보 불러오기
    public ItemForm getItemDtl (Long itemId) {
        ItemDto itemDto = itemMapper.selectItem(itemId);

        if(itemDto == null)
            throw new NullPointerException("상품이 존재하지 않습니다");

        ItemForm itemForm = makeItemForm(itemDto);

        itemForm.setItemImgList(itemMapper.selectItemImg(itemId));

        return itemForm;
    }

    public Long updateItem(ItemForm itemForm
                            , List<MultipartFile> itemImgFileList) throws Exception {
        ItemDto itemDto = makeItemDto(itemForm);

        int result = itemMapper.updateItem(itemDto);

        List<Long> itemImgIds = itemForm.getItemImgIds();

        for (int i=0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return itemDto.getItemId();
    }


    //ItemForm -> ItemDto 변환
    private ItemDto makeItemDto(ItemForm itemForm) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemId(itemForm.getId());
        itemDto.setItemName(itemForm.getItemName());
        itemDto.setPrice(itemForm.getPrice());
        itemDto.setStockNumber(itemForm.getStockNumber());
        itemDto.setItemDetail(itemForm.getItemDetail());
        itemDto.setItemSellStatus(itemForm.getItemSellStatus());

        return itemDto;
    }

    //ItemDto-> ItemForm 변환
    private ItemForm makeItemForm(ItemDto itemDto) {
        ItemForm itemForm = new ItemForm();

        itemForm.setId(itemDto.getItemId());
        itemForm.setItemName(itemDto.getItemName());
        itemForm.setPrice(itemDto.getPrice());
        itemForm.setStockNumber(itemDto.getStockNumber());
        itemForm.setItemDetail(itemDto.getItemDetail());
        itemForm.setItemSellStatus(itemDto.getItemSellStatus());

        return itemForm;
    }

    public List<ItemDto> itemListPage(Map map){
        return itemMapper.itemListPage(map);
    }

    public int countAdminItems(Map map){
        return itemMapper.countAdminItems(map);
    }


}

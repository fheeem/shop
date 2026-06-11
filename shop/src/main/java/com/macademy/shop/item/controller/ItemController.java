package com.macademy.shop.item.controller;

import com.macademy.shop.config.PageHandler;
import com.macademy.shop.item.dto.ItemDto;
import com.macademy.shop.item.dto.ItemSearchDto;
import com.macademy.shop.item.form.ItemForm;
import com.macademy.shop.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "/item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemForm itemForm,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemForm.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입니다.");
            return "item/itemForm";
        }

        try{
            itemService.itemInsert(itemForm, itemImgFileList);

        } catch(Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/item/{itemId}")//error
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try{
            ItemForm itemForm = itemService.getItemDtl(itemId);

            model.addAttribute("itemForm", itemForm);
        } catch (NullPointerException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다");
            model.addAttribute("itemForm", new ItemForm());
            return "item/itemForm";
        }

        return "item/itemForm";
    }

    @PostMapping (value = "/admin/item/{itemId}")//error
    public String itemUpdate(@Valid ItemForm itemForm
                                , BindingResult bindingResult
                                , @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList
                                , Model model){
        if (bindingResult.hasErrors())
            return "item/itemForm";

        if (itemImgFileList.get(0).isEmpty() && itemForm.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입니다");
            return "item/itemForm";
        }

            try {
                itemService.updateItem(itemForm, itemImgFileList);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다");
                e.printStackTrace();
                return "item/itemForm";
            }
            return "redirect:/";
     }
     @GetMapping (value= {"/admin/items", "/admin/items/{page}"})
    public String itemListPage(@PathVariable(value = "page", required = false) Integer page
                               , @ModelAttribute ItemSearchDto itemSearchDto
                               , Model model){

        int ps = 10;

         Map map = new HashMap();


         if(page == null)
             page =1;
         //1페이지 -> offset 0
         //2페이지 -> offset 10
         map.put("pageSize", ps);
         map.put("offset", page * ps - ps);
         map.put("itemSearchDto", itemSearchDto);


         int totalCnt = itemService.countAdminItems(map);
         PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

         List<ItemDto> items = itemService.itemListPage(map);


        model.addAttribute("items", items);
        model.addAttribute("pageHandler", pageHandler);

        return "item/itemMng";
     }

     @GetMapping("/item/{itemId}")
    public String itemShow(@PathVariable("itemId") Long itemId,
                                Model model){
        ItemForm itemForm = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemForm);

        return "item/itemDtl";
     }
}

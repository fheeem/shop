package com.macademy.shop;

import com.macademy.shop.config.PageHandler;
import com.macademy.shop.item.dto.ItemSearchDto;
import com.macademy.shop.item.service.ItemService;
import com.macademy.shop.main.dto.MainItemDto;
import com.macademy.shop.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final ItemService itemService;
    private final MainService mainService;

    @GetMapping("/")
    public String main(@RequestParam(value = "page", required = false) Integer page
                        ,@RequestParam(value = "searchText", required = false) String searchText
                        ,@ModelAttribute("itemSearchDto") ItemSearchDto itemSearchDto
                        ,Model model) {

                    Map map = new HashMap();

                    int pg = 6;
                    if(searchText != null){
                        itemSearchDto.setSearchBy("itemName");
                        itemSearchDto.setSearchText(searchText);
                    }

                    if(page == null)
                        page = 1;

                    map.put("offset", page * pg - pg);
                    map.put("pageSize", pg);
                    map.put("itemSearchDto", itemSearchDto);


                    int totalCnt = itemService.countAdminItems(map);
                    PageHandler pa = new PageHandler(totalCnt, pg, page);

                    List<MainItemDto> items = mainService.mainSelect(map);

                    model.addAttribute("items", items);
                    model.addAttribute("pageHandler", pa);
                    model.addAttribute("itemSearchDto", itemSearchDto );
                    model.addAttribute("maxPage", 5 );

                return "index";
        }

    }


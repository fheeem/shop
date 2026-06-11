package com.macademy.shop.main.mapper;

import com.macademy.shop.main.dto.MainItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainMapper {
    List<MainItemDto> mainItemSelect(Map map);
}

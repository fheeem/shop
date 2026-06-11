package com.macademy.shop.main.service;

import com.macademy.shop.main.dto.MainItemDto;
import com.macademy.shop.main.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MainService {
    private final MainMapper mainMapper;

    //트렌젝션을 읽기 전용으로 설정
    @Transactional(readOnly = true)
    public List<MainItemDto> mainSelect(Map map) {
        return  mainMapper.mainItemSelect(map);
    }
}

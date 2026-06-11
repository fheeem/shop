package com.macademy.shop.item.service;

import com.macademy.shop.item.dto.ItemImgDto;
import com.macademy.shop.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor    //주요 필드에 대한 생성자를 자동으로 만들어주는 어노테이션
public class ItemImgService {

    @Value("${file.upload.itemImgLocation}")
    private String itemImgLocation;

    private final ItemMapper itemMapper;

    private final FileService fileService;

    public void saveItemImg(ItemImgDto itemImgDto, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(itemImgDto);

        //사용자가 파일을 등록했으면
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                                                        itemImgFile.getBytes());

            imgUrl = "/img/item/" + imgName;
        }

        itemImgDto.setImgName(imgName);
        itemImgDto.setOriImgName(oriImgName);
        itemImgDto.setImgUrl(imgUrl);

        itemMapper.insertItemImg(itemImgDto);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            ItemImgDto savedItemImg = itemMapper.selectItemImgId(itemImgId);

            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/"
                                            + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation
                                        , oriImgName, itemImgFile.getBytes());

            String imgUrl = "/img/item/" + imgName;

            savedItemImg.setOriImgName(oriImgName);
            savedItemImg.setImgName(imgName);
            savedItemImg.setImgUrl(imgUrl);

            itemMapper.updateItemImg(savedItemImg);
        }
    }


}

package com.macademy.shop.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PageHandler {

    private int totalCnt; //총 개시물 개수
    private int pageSize=10; //한 페이지의 크기
    private int naviSize=10; // 페이지 네비게이션의 크기
    private int totalPage;   // 전체 페이지의 개수
    private int page;        // 현재 페이지
    private int beginPage;   // 네비게이션의 첫번째 페이지 번호
    private int endPage;   // 네비게이션의 마지막 페이지 번호
    private boolean firstPage;   // 첫 페이지인지 확인
    private boolean lastPage;   // 마지막 페이지인지 확인

    public PageHandler(int totalCnt, int pageSize, int page){
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        // 전체 페이지 개수
        totalPage = (int) Math.ceil(totalCnt / pageSize);

        //네비게이션의 첫번째 페이지
        beginPage = totalCnt == 0 ? 0 :(page-1) / naviSize * naviSize * naviSize + 1;

        //네이게이션의 마지막 페이지
        endPage = Math.min(beginPage + naviSize -1, totalPage);

        firstPage = (totalCnt == 0) || (beginPage == 1);

        lastPage = (totalPage == 0) || (endPage == totalPage);
    }
}

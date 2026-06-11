package com.macademy.shop;

import com.macademy.shop.config.PageHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PageHandlerTest {

    //기본테스트
    @Test
    @DisplayName("첫번째 페이지 테스트")
    void test1(){
        PageHandler ph = new PageHandler(250, 10, 1);
        assertThat(ph.getBeginPage()).isEqualTo(1);
        assertThat(ph.getEndPage()).isEqualTo(10);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }

    @Test
    @DisplayName("중간 페이지 테스트")
    void test2(){
        PageHandler ph = new PageHandler(250, 10, 1);
        assertThat(ph.getBeginPage()).isEqualTo(1);
        assertThat(ph.getEndPage()).isEqualTo(10);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }


    //경계값 테스트
    @Test
    @DisplayName("경계값 페이지 테스트")
    void test3(){
        PageHandler ph = new PageHandler(250, 10, 11);
        assertThat(ph.getBeginPage()).isEqualTo(11);
        assertThat(ph.getEndPage()).isEqualTo(10);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }
    // 마지막 페이지
    @Test
    @DisplayName("마지막 페이지 테스트")
    void test4(){
        PageHandler ph = new PageHandler(250, 10, 11);
        assertThat(ph.getBeginPage()).isEqualTo(11);
        assertThat(ph.getEndPage()).isEqualTo(20);
        assertThat(ph.isFirstPage()).isTrue();

    }

    @Test
    @DisplayName("마지막 페이지 테스트")
    void test5(){
        PageHandler ph = new PageHandler(255, 10, 20);
        assertThat(ph.getBeginPage()).isEqualTo(1);
        assertThat(ph.getEndPage()).isEqualTo(10);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }

    @Test
    @DisplayName("마지막 페이지 테스트")
    void test6(){
        PageHandler ph = new PageHandler(255, 10, 26);
        assertThat(ph.getBeginPage()).isEqualTo(21);
        assertThat(ph.getEndPage()).isEqualTo(26);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }
    // 예외값
    @Test
    @DisplayName("예외값 테스트")
    void test7(){
        PageHandler ph = new PageHandler(0, 10, 0);
        assertThat(ph.getBeginPage()).isEqualTo(0);
        assertThat(ph.getEndPage()).isEqualTo(0);
        assertThat(ph.isFirstPage()).isTrue();
        assertThat(ph.isLastPage()).isFalse();
    }

}

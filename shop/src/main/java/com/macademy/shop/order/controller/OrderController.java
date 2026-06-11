package com.macademy.shop.order.controller;


import com.macademy.shop.config.PageHandler;
import com.macademy.shop.member.mapper.MemberMapper;
import com.macademy.shop.member.service.MemberService;
import com.macademy.shop.order.dto.OrderHistDto;
import com.macademy.shop.order.form.OrderForm;
import com.macademy.shop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    //Principal : 현재 인증된 사용자를 나타내는 객체
    //@ResponseBody : 컨트롤러에서 뷰의 이름이 아닌 다른 객체를 인턴하고자 할때 사용
    //ResponseEntity : 객체와 같은 데이터를 반환하는 컨트롤러에서 JSON형태로 변환하여 응답 본문에 포함하여 전달
    // (HTTP상태코드 헤더 바디)
    // 1xx : 중간 단계 알림 , 2xx : 정상 처리, 3xx : 리다이렉트용 4xx : 클라이언트 문제 , 5xx : 서버문제

    //집합타입이 ResponseEntity 이거나 httpEntity이면
    //@ResponseBody나 @RestController가 없어도 무조건 Http응답으로 처리

    //@RequestBody : 프론트 -> 서버 요청
    //@ResponseBody : 서버 -> 프론트 응답
    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderForm orderForm
                                            , BindingResult bindingResult
                                            , Principal principal){

        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String id = principal.getName();

        Long orderId;

        try {
            orderId = orderService.createOrder(orderForm, id);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable(value = "page", required = false)Integer page
                            , @ModelAttribute("orderHistDto")OrderHistDto orderHistDto
                            , Principal principal
                            , Model model) {
        int ps = 4;
        Map map = new HashMap();
        if(page == null) page = 1;

        map.put("offset", page * ps - ps);
        map.put("pageSize", ps);

        Long memberId = memberService.findMemberId(principal.getName());

        map.put("memberId", memberId);

        int totalCnt = orderService.orderCount(map);
        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        List<OrderHistDto> orderHist = orderService.orderSelect(map);

        model.addAttribute("page", page);
        model.addAttribute("orderHist", orderHist);
        model.addAttribute("pageHandler", pageHandler);

        return "order/orderHist";

    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId")Long orderId
                                        , Principal principal) {
        if(!orderService.validateOrder(orderId,principal.getName())){
            return new ResponseEntity<String>("주문취소 권한없다", HttpStatus.FORBIDDEN);
        }

        try {
            orderService.cancelOrder(orderId);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}

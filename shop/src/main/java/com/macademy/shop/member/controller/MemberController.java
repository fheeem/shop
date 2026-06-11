package com.macademy.shop.member.controller;

import com.macademy.shop.member.constant.Role;
import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.form.MemberJoinForm;
import com.macademy.shop.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberJoinForm", new MemberJoinForm());
        return "member/memberJoinForm";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberJoinForm memberJoinForm,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes rttr) {

        if (bindingResult.hasErrors()) {
            return "member/memberJoinForm";
        }

        try {
            MemberDto dto = new MemberDto();
            dto.setId(memberJoinForm.getId());
            dto.setPassword(memberJoinForm.getPassword());
            dto.setName(memberJoinForm.getName());
            dto.setEmail(memberJoinForm.getEmail());
            dto.setAddress(memberJoinForm.getAddress());
            dto.setRole(Role.USER);

            memberService.insertMember(dto);

            rttr.addFlashAttribute("resultMessage", "회원가입을 환영합니다.");

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberJoinForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}

package com.macademy.shop;

import com.macademy.shop.member.constant.Role;
import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.service.MemberSecurityService;
import com.macademy.shop.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   //HTTP요청을 모방하여 컨트롤러의 동작을 테스트할 때 사용
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertMemberTest() {
        MemberDto member = new MemberDto();
        member.setId("test2");
        member.setPassword("1234");
        member.setName("김자바");
        member.setEmail("test2@naver.com");
        member.setAddress("울산");
        member.setRole(Role.USER);

        int result = memberService.insertMember(member);
        System.out.println(member);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void overlapTest() {
        MemberDto member1 = new MemberDto();
        member1.setId("test4");
        member1.setPassword("1111");
        member1.setName("정디비");
        member1.setEmail("test4@naver.com");
        member1.setAddress("부산");
        member1.setRole(Role.USER);

        memberService.insertMember(member1);

        MemberDto newMember = new MemberDto();
        newMember.setId("test5");
        newMember.setEmail("test4@naver.com");

        Throwable th = null;

        try {
            memberService.insertMember(newMember);
        } catch (IllegalStateException e) {
            th = e;
        }
        System.out.println(th.getMessage());
        assertThat(th.getMessage()).isIn("중복된 아이디입니다.", "이미 가입된 회원입니다.");

    }

    @Test
    public void loginMemberTest() throws Exception {
        String id="test7";
        String password="77";

        mockMvc.perform(formLogin()
                        .userParameter("id")
                        .loginProcessingUrl("/members/login")
                        .user(id)
                        .password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

        mockMvc.perform(post("/members/logout")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}

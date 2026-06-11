package com.macademy.shop.member.service;

import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int insertMember(MemberDto memberDto) {

        this.overlapId(memberDto.getId());
        this.overlapEmail(memberDto.getEmail());

        //BCryptPasswordEncoder : 시큐리티에서 제공하는 클래스. 비밀번호를 암호화하는데 사용
        //                        복호화 불가능한 단방향 해시 방식
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //encode : 패스워드를 암호화하는 메서드
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

        //DTO에 저장된 패스워드를 암호화된 비밀번호로 교체
        memberDto.setPassword(encodedPassword);

        return memberMapper.insertMember(memberDto);
    }

    public void overlapId(String id) {
        MemberDto findId = memberMapper.overlapId(id);

        if (findId != null)
            throw new IllegalStateException("중복된 아이디입니다.");
    }

    public void overlapEmail(String email) {
        MemberDto findEmail = memberMapper.overlapEmail(email);

        if (findEmail != null)
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }

    public Long findMemberId(String id) {
        return Long.valueOf(memberMapper.findMemberId(id));
    }
}

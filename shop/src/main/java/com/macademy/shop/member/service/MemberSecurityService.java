package com.macademy.shop.member.service;

import com.macademy.shop.member.constant.Role;
import com.macademy.shop.member.dto.MemberDto;
import com.macademy.shop.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberSecurityService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberDto member = memberMapper.loginMember(username);

        if (member == null) {
            throw  new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 방법 1
        //GrantedAuthority : 사용자가 가지는 권한을 나타내는 인터페이스
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if ("ADMIN".equals(member.getRole().toString()))
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN));
//        else
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.USER));
//
//        return new User(member.getId(), member.getPassword(), authorities);

        //방법 2
        //roles()를 사용하여 권한을 설정하면
        //new SimpleGrantedAuthority("ROLE_" + )를 시큐리티가 대신 생성하여 설정
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }
}

package com.macademy.shop.member.dto;

import com.macademy.shop.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private Role role;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}

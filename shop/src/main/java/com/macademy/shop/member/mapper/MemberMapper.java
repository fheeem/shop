package com.macademy.shop.member.mapper;

import com.macademy.shop.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Member;

@Mapper
public interface MemberMapper {
    int insertMember(MemberDto memberDto);

    MemberDto overlapId(String id);

    MemberDto overlapEmail(String email);

    MemberDto loginMember(String id);

    Long findMemberId(String id);
}

package com.example.library.mapper;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.entity.Member;

/**
 * Mapper for converting between Member entity and DTOs.
 */
public class MemberMapper {

    public static MemberDto toDto(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.isActive(),
                member.getRegistrationDate()
        );
    }

    public static Member toEntity(MemberCreateDto dto) {
        Member member = new Member();
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        member.setAddress(dto.address());
        return member;
    }
}
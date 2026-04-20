package com.example.library.mapper;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.entity.Member;

public final class MemberMapper {

    private MemberMapper() {
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getFirstName(),
                member.getLastName(),
                member.getFullName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.isActive(),
                member.getMaxAllowedLoans(),
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

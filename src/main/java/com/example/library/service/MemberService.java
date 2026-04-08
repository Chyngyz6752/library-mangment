package com.example.library.service;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.entity.Member;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.MemberMapper;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing library members.
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public MemberDto createMember(MemberCreateDto memberDto) {
        Member member = MemberMapper.toEntity(memberDto);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toDto(savedMember);
    }
}
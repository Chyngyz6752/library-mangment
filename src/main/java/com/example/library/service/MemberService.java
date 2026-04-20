package com.example.library.service;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.dto.MemberUpdateDto;
import com.example.library.entity.Member;
import com.example.library.exception.DuplicateResourceException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.MemberMapper;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<MemberDto> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberMapper::toDto);
    }

    @Transactional(readOnly = true)
    public MemberDto getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public MemberDto createMember(MemberCreateDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.email())) {
            throw new DuplicateResourceException("Member with email " + memberDto.email() + " already exists");
        }
        if (memberDto.phone() != null && !memberDto.phone().isBlank()
                && memberRepository.existsByPhone(memberDto.phone())) {
            throw new DuplicateResourceException("Member with phone " + memberDto.phone() + " already exists");
        }
        Member member = MemberMapper.toEntity(memberDto);
        member.setRegistrationDate(LocalDate.now());
        Member saved = memberRepository.save(member);
        log.info("Created member id={}, email={}", saved.getMemberId(), saved.getEmail());
        return MemberMapper.toDto(saved);
    }

    @Transactional
    public MemberDto updateMember(Long id, MemberUpdateDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        if (dto.firstName() != null) member.setFirstName(dto.firstName());
        if (dto.lastName() != null) member.setLastName(dto.lastName());
        if (dto.email() != null && !dto.email().equals(member.getEmail())) {
            if (memberRepository.existsByEmail(dto.email())) {
                throw new DuplicateResourceException("Email already in use: " + dto.email());
            }
            member.setEmail(dto.email());
        }
        if (dto.phone() != null && !dto.phone().equals(member.getPhone())) {
            if (!dto.phone().isBlank() && memberRepository.existsByPhone(dto.phone())) {
                throw new DuplicateResourceException("Phone already in use: " + dto.phone());
            }
            member.setPhone(dto.phone().isBlank() ? null : dto.phone());
        }
        if (dto.address() != null) member.setAddress(dto.address());
        if (dto.maxAllowedLoans() != null) member.setMaxAllowedLoans(dto.maxAllowedLoans());
        if (dto.active() != null) member.setActive(dto.active());

        log.info("Updated member id={}", id);
        return MemberMapper.toDto(member);
    }

    @Transactional
    public void deactivateMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        member.setActive(false);
        log.info("Deactivated member id={}", id);
    }
}

package com.example.library.controller;

import com.example.library.dto.MemberCreateDto;
import com.example.library.dto.MemberDto;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing library members.
 * Works exclusively with DTOs.
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@Valid @RequestBody MemberCreateDto memberDto) {
        MemberDto createdMember = memberService.createMember(memberDto);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }
}
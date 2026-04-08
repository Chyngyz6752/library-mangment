package com.example.library.dto;

import java.time.LocalDate;

/**
 * DTO for representing member information.
 */
public record MemberDto(
    Long memberId,
    String firstName,
    String lastName,
    String email,
    String phone,
    boolean isActive,
    LocalDate registrationDate
) {}
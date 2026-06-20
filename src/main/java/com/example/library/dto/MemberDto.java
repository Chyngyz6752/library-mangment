package com.example.library.dto;

import java.time.LocalDate;

/**
 * DTO читателя для ответа API.
 */

public record MemberDto(
        Long memberId,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String phone,
        String address,
        boolean active,
        int maxAllowedLoans,
        LocalDate registrationDate
) {
}

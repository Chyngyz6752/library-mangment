package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new member.
 */
public record MemberCreateDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Email String email,
    String phone,
    String address
) {}
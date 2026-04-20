package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MemberUpdateDto(
        @Size(max = 100) String firstName,
        @Size(max = 100) String lastName,
        @Email @Size(max = 255) String email,
        @Pattern(regexp = "^[+\\d\\s()\\-]{5,30}$", message = "Invalid phone format") String phone,
        @Size(max = 500) String address,
        @Positive Integer maxAllowedLoans,
        Boolean active
) {
}

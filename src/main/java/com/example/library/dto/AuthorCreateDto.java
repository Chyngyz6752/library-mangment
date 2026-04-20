package com.example.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AuthorCreateDto(
        @NotBlank @Size(max = 200) String fullName,
        @Positive @Max(2100) Integer birthYear,
        @Positive @Max(2100) Integer deathYear,
        String biography
) {
}

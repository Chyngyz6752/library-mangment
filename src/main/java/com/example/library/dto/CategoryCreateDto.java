package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDto(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 2000) String description
) {
}

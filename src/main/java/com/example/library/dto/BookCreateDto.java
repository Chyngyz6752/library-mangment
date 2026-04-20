package com.example.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookCreateDto(
        @NotBlank @Size(max = 255) String title,
        @Size(max = 255) String subtitle,
        @NotBlank
        @Pattern(regexp = "^(?:\\d{9}[\\dXx]|\\d{13}|\\d{3}-\\d{10}|\\d{3}-\\d{1}-\\d{3}-\\d{5}-\\d{1})$",
                message = "ISBN must be a valid ISBN-10 or ISBN-13")
        String isbn,
        @Size(max = 255) String publisher,
        @NotNull @Positive @Max(2100) Integer publishYear,
        @Size(max = 50) String language,
        @NotNull @Positive Integer pages,
        @NotNull @Positive Integer totalCopies,
        @NotNull Long categoryId
) {
}

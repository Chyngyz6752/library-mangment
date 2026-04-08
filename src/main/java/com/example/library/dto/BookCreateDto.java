package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for creating or updating a book.
 */
public record BookCreateDto(
    @NotBlank String title,
    String subtitle,
    @NotBlank String isbn,
    String publisher,
    @NotNull @Positive int publishYear,
    String language,
    @NotNull @Positive int pages,
    @NotNull @Positive int totalCopies,
    @NotNull Long categoryId
) {}
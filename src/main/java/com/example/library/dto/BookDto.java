package com.example.library.dto;

import java.time.LocalDateTime;

public record BookDto(
        Long bookId,
        String title,
        String subtitle,
        String isbn,
        Integer publishYear,
        Integer totalCopies,
        Long availableCopies,
        String publisher,
        String language,
        Integer pages,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {
}

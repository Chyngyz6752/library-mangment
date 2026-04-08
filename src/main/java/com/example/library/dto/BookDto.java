package com.example.library.dto;

/**
 * DTO для представления информации о книге.
 */
public record BookDto(
    Long bookId,
    String title,
    String isbn,
    int publishYear,
    int totalCopies,
    String publisher,
    String language,
    int pages
) {}
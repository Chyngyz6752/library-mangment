package com.example.library.dto;

/**
 * DTO автора для ответа API.
 */

public record AuthorDto(
        Long authorId,
        String fullName,
        Integer birthYear,
        Integer deathYear,
        String biography
) {
}

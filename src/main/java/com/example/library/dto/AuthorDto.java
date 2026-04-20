package com.example.library.dto;

public record AuthorDto(
        Long authorId,
        String fullName,
        Integer birthYear,
        Integer deathYear,
        String biography
) {
}

package com.example.library.dto;

/**
 * DTO категории для ответа API.
 */

public record CategoryDto(
        Long categoryId,
        String name,
        String description
) {
}

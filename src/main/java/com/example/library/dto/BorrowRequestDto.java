package com.example.library.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO запроса на выдачу книги (идентификаторы читателя и книги/экземпляра).
 */

public record BorrowRequestDto(
        @NotNull Long memberId,
        @NotNull Long copyId
) {
}

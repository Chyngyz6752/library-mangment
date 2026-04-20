package com.example.library.dto;

import jakarta.validation.constraints.NotNull;

public record BorrowRequestDto(
        @NotNull Long memberId,
        @NotNull Long copyId
) {
}

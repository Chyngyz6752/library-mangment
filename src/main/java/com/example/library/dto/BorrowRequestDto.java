package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO для запроса на выдачу книги.
 */
@Data
public class BorrowRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private Long copyId;
}
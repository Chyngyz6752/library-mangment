package com.example.library.dto;

import com.example.library.enums.BookCopyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookCopyCreateDto(
        @NotNull Long bookId,
        @NotBlank @Size(max = 50) String barcode,
        @Size(max = 50) String accessionNumber,
        BookCopyStatus status,
        @Size(max = 100) String location
) {
}

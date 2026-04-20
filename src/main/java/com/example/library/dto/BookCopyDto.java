package com.example.library.dto;

import com.example.library.enums.BookCopyStatus;

import java.time.LocalDateTime;

public record BookCopyDto(
        Long copyId,
        Long bookId,
        String bookTitle,
        String barcode,
        String accessionNumber,
        BookCopyStatus status,
        String location,
        LocalDateTime addedAt
) {
}

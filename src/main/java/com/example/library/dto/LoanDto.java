package com.example.library.dto;

import com.example.library.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanDto(
        Long loanId,
        Long copyId,
        String bookTitle,
        Long memberId,
        String memberFullName,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        LoanStatus status,
        BigDecimal fineAmount
) {
}

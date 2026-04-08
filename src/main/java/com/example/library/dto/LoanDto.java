package com.example.library.dto;

import com.example.library.enums.LoanStatus;
import java.time.LocalDate;

/**
 * DTO for representing a loan.
 */
public record LoanDto(
    Long loanId,
    Long copyId,
    Long memberId,
    LocalDate loanDate,
    LocalDate dueDate,
    LocalDate returnDate,
    LoanStatus status
) {}
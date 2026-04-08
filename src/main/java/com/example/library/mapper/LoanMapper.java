package com.example.library.mapper;

import com.example.library.dto.LoanDto;
import com.example.library.entity.Loan;

/**
 * Mapper for converting between Loan entity and DTOs.
 */
public class LoanMapper {

    public static LoanDto toDto(Loan loan) {
        return new LoanDto(
                loan.getLoanId(),
                loan.getCopy().getCopyId(),
                loan.getMember().getMemberId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }
}
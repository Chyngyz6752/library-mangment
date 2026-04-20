package com.example.library.mapper;

import com.example.library.dto.LoanDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static LoanDto toDto(Loan loan) {
        BookCopy copy = loan.getCopy();
        Book book = copy != null ? copy.getBook() : null;
        Member member = loan.getMember();
        return new LoanDto(
                loan.getLoanId(),
                copy != null ? copy.getCopyId() : null,
                book != null ? book.getTitle() : null,
                member != null ? member.getMemberId() : null,
                member != null ? member.getFullName() : null,
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                loan.getFineAmount()
        );
    }
}

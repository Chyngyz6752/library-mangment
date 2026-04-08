package com.example.library.service;

import com.example.library.dto.LoanDto;
import com.example.library.entity.BookCopy;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.enums.BookCopyStatus;
import com.example.library.enums.LoanStatus;
import com.example.library.exception.CopyNotAvailableException;
import com.example.library.exception.MemberLimitExceededException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.LoanMapper;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

/**
 * Service for managing book loans.
 */
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookCopyRepository bookCopyRepository;

    @Transactional
    public LoanDto borrowBook(Long memberId, Long copyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!member.isActive()) {
            throw new MemberLimitExceededException("Member is not active.");
        }

        long activeLoans = loanRepository.countByMemberAndStatus(member, LoanStatus.ACTIVE);
        if (activeLoans >= member.getMaxAllowedLoans()) {
            throw new MemberLimitExceededException("Member has reached the maximum number of allowed loans.");
        }

        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + copyId));

        if (copy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new CopyNotAvailableException("Book copy is not available for loan.");
        }

        copy.setStatus(BookCopyStatus.BORROWED);
        bookCopyRepository.save(copy);

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setCopy(copy);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus(LoanStatus.ACTIVE);

        Loan savedLoan = loanRepository.save(loan);
        return LoanMapper.toDto(savedLoan);
    }

    @Transactional
    public LoanDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);

        BookCopy copy = loan.getCopy();
        copy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopyRepository.save(copy);

        Loan updatedLoan = loanRepository.save(loan);
        return LoanMapper.toDto(updatedLoan);
    }
}
package com.example.library.service;

import com.example.library.entity.BookCopy;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.enums.BookCopyStatus;
import com.example.library.enums.LoanStatus;
import com.example.library.exception.CopyNotAvailableException;
import com.example.library.exception.MemberLimitExceededException;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void borrowBook_Success() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setActive(true);
        member.setMaxAllowedLoans(5);

        BookCopy copy = new BookCopy();
        copy.setCopyId(1L);
        copy.setStatus(BookCopyStatus.AVAILABLE);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookCopyRepository.findById(1L)).thenReturn(Optional.of(copy));
        when(loanRepository.countByMemberAndStatus(member, LoanStatus.ACTIVE)).thenReturn(0L);

        loanService.borrowBook(1L, 1L);

        verify(bookCopyRepository, times(1)).save(copy);
        assertEquals(BookCopyStatus.BORROWED, copy.getStatus());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void borrowBook_CopyNotAvailable() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setActive(true);

        BookCopy copy = new BookCopy();
        copy.setCopyId(1L);
        copy.setStatus(BookCopyStatus.BORROWED);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookCopyRepository.findById(1L)).thenReturn(Optional.of(copy));
        when(loanRepository.countByMemberAndStatus(member, LoanStatus.ACTIVE)).thenReturn(0L);

        assertThrows(CopyNotAvailableException.class, () -> {
            loanService.borrowBook(1L, 1L);
        });
    }

    @Test
    void borrowBook_MemberLimitExceeded() {
        Member member = new Member();
        member.setMemberId(1L);
        member.setActive(true);
        member.setMaxAllowedLoans(1);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(loanRepository.countByMemberAndStatus(member, Loan.Status.ACTIVE)).thenReturn(1L);

        assertThrows(MemberLimitExceededException.class, () -> {
            loanService.borrowBook(1L, 1L);
        });
    }

    @Test
    void returnBook_Success() {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setReturnDate(null);
        loan.setStatus(LoanStatus.ACTIVE);

        BookCopy copy = new BookCopy();
        copy.setStatus(BookCopyStatus.BORROWED);
        loan.setCopy(copy);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.returnBook(1L);

        assertNotNull(loan.getReturnDate());
        assertEquals(LoanStatus.RETURNED, loan.getStatus());
        assertEquals(BookCopyStatus.AVAILABLE, copy.getStatus());
        verify(loanRepository, times(1)).save(loan);
        verify(bookCopyRepository, times(1)).save(copy);
    }
}
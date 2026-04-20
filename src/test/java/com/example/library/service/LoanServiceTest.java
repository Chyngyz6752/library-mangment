package com.example.library.service;

import com.example.library.config.LibraryProperties;
import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.enums.BookCopyStatus;
import com.example.library.enums.LoanStatus;
import com.example.library.exception.BusinessException;
import com.example.library.exception.CopyNotAvailableException;
import com.example.library.exception.MemberLimitExceededException;
import com.example.library.exception.MemberNotActiveException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock private LoanRepository loanRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private BookCopyRepository bookCopyRepository;

    private final LibraryProperties props = new LibraryProperties();

    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService(loanRepository, memberRepository, bookCopyRepository, props);
    }

    private Member activeMember() {
        Member m = new Member();
        m.setMemberId(1L);
        m.setFirstName("Test");
        m.setLastName("User");
        m.setActive(true);
        m.setMaxAllowedLoans(5);
        return m;
    }

    private BookCopy availableCopy() {
        BookCopy c = new BookCopy();
        c.setCopyId(1L);
        c.setStatus(BookCopyStatus.AVAILABLE);
        Book b = new Book();
        b.setBookId(1L);
        b.setTitle("Test Book");
        c.setBook(b);
        return c;
    }

    @Test
    void borrow_success_setsBorrowedAndCreatesLoan() {
        Member m = activeMember();
        BookCopy c = availableCopy();
        when(memberRepository.findById(1L)).thenReturn(Optional.of(m));
        when(bookCopyRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(c));
        when(loanRepository.countByMemberAndStatus(m, LoanStatus.ACTIVE)).thenReturn(0L);
        when(loanRepository.save(any(Loan.class))).thenAnswer(inv -> {
            Loan saved = inv.getArgument(0);
            saved.setLoanId(100L);
            return saved;
        });

        loanService.borrowBook(1L, 1L);

        assertEquals(BookCopyStatus.BORROWED, c.getStatus());
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void borrow_copyUnavailable_throws() {
        Member m = activeMember();
        BookCopy c = availableCopy();
        c.setStatus(BookCopyStatus.BORROWED);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(m));
        when(bookCopyRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(c));
        when(loanRepository.countByMemberAndStatus(m, LoanStatus.ACTIVE)).thenReturn(0L);

        assertThrows(CopyNotAvailableException.class, () -> loanService.borrowBook(1L, 1L));
    }

    @Test
    void borrow_memberLimitExceeded_throws() {
        Member m = activeMember();
        m.setMaxAllowedLoans(1);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(m));
        when(loanRepository.countByMemberAndStatus(m, LoanStatus.ACTIVE)).thenReturn(1L);

        assertThrows(MemberLimitExceededException.class, () -> loanService.borrowBook(1L, 1L));
    }

    @Test
    void borrow_inactiveMember_throws() {
        Member m = activeMember();
        m.setActive(false);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(m));

        assertThrows(MemberNotActiveException.class, () -> loanService.borrowBook(1L, 1L));
    }

    @Test
    void borrow_unknownMember_throws() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loanService.borrowBook(99L, 1L));
    }

    @Test
    void return_active_setsReturnedAndResetsCopy() {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setDueDate(LocalDate.now().plusDays(5));
        BookCopy c = availableCopy();
        c.setStatus(BookCopyStatus.BORROWED);
        loan.setCopy(c);
        Member m = activeMember();
        loan.setMember(m);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.returnBook(1L);

        assertNotNull(loan.getReturnDate());
        assertEquals(LoanStatus.RETURNED, loan.getStatus());
        assertEquals(BookCopyStatus.AVAILABLE, c.getStatus());
        assertEquals(BigDecimal.ZERO, loan.getFineAmount());
    }

    @Test
    void return_alreadyReturned_throws() {
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setStatus(LoanStatus.RETURNED);
        loan.setCopy(availableCopy());
        loan.setMember(activeMember());
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        assertThrows(BusinessException.class, () -> loanService.returnBook(1L));
    }

    @Test
    void return_overdue_calculatesFine() {
        props.setFinePerDay(new BigDecimal("1.00"));
        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setStatus(LoanStatus.OVERDUE);
        loan.setDueDate(LocalDate.now().minusDays(3));
        BookCopy c = availableCopy();
        c.setStatus(BookCopyStatus.BORROWED);
        loan.setCopy(c);
        loan.setMember(activeMember());
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.returnBook(1L);

        assertEquals(new BigDecimal("3.00"), loan.getFineAmount());
        assertEquals(LoanStatus.RETURNED, loan.getStatus());
    }
}

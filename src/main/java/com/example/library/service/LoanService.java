package com.example.library.service;

import com.example.library.config.LibraryProperties;
import com.example.library.dto.LoanDto;
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
import com.example.library.mapper.LoanMapper;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookCopyRepository bookCopyRepository;
    private final LibraryProperties libraryProperties;

    @Transactional
    public LoanDto borrowBook(Long memberId, Long copyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!member.isActive()) {
            throw new MemberNotActiveException("Member " + memberId + " is not active");
        }

        long activeLoans = loanRepository.countByMemberAndStatus(member, LoanStatus.ACTIVE);
        if (activeLoans >= member.getMaxAllowedLoans()) {
            throw new MemberLimitExceededException(
                    "Member has reached the maximum number of allowed loans: " + member.getMaxAllowedLoans());
        }

        BookCopy copy = bookCopyRepository.findByIdForUpdate(copyId)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + copyId));

        if (copy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new CopyNotAvailableException(
                    "Book copy " + copyId + " is not available (status: " + copy.getStatus() + ")");
        }

        copy.setStatus(BookCopyStatus.BORROWED);

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setCopy(copy);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(libraryProperties.getDurationDays()));
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setFineAmount(BigDecimal.ZERO);

        Loan saved = loanRepository.save(loan);
        log.info("Borrow: loanId={}, memberId={}, copyId={}, due={}",
                saved.getLoanId(), memberId, copyId, saved.getDueDate());
        return LoanMapper.toDto(saved);
    }

    @Transactional
    public LoanDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));

        if (loan.getStatus() != LoanStatus.ACTIVE && loan.getStatus() != LoanStatus.OVERDUE) {
            throw new BusinessException(
                    "Loan " + loanId + " cannot be returned (status: " + loan.getStatus() + ")");
        }

        LocalDate today = LocalDate.now();
        loan.setReturnDate(today);
        loan.setStatus(LoanStatus.RETURNED);
        loan.setFineAmount(calculateFine(loan.getDueDate(), today));

        BookCopy copy = loan.getCopy();
        if (copy.getStatus() == BookCopyStatus.BORROWED) {
            copy.setStatus(BookCopyStatus.AVAILABLE);
        }

        log.info("Return: loanId={}, fine={}", loanId, loan.getFineAmount());
        return LoanMapper.toDto(loan);
    }

    @Transactional(readOnly = true)
    public Page<LoanDto> getLoans(LoanStatus status, Pageable pageable) {
        Page<Loan> page = (status == null)
                ? loanRepository.findAll(pageable)
                : loanRepository.findByStatus(status, pageable);
        return page.map(LoanMapper::toDto);
    }

    @Transactional(readOnly = true)
    public LoanDto getLoanById(Long id) {
        return loanRepository.findById(id)
                .map(LoanMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    @Transactional
    public int markOverdueLoans() {
        LocalDate today = LocalDate.now();
        List<Loan> toMark = loanRepository.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, today);
        for (Loan loan : toMark) {
            loan.setStatus(LoanStatus.OVERDUE);
            loan.setFineAmount(calculateFine(loan.getDueDate(), today));
        }
        if (!toMark.isEmpty()) {
            log.info("Marked {} loans as OVERDUE", toMark.size());
        }
        return toMark.size();
    }

    private BigDecimal calculateFine(LocalDate dueDate, LocalDate referenceDate) {
        if (dueDate == null || referenceDate == null || !referenceDate.isAfter(dueDate)) {
            return BigDecimal.ZERO;
        }
        long daysLate = ChronoUnit.DAYS.between(dueDate, referenceDate);
        return libraryProperties.getFinePerDay().multiply(BigDecimal.valueOf(daysLate));
    }
}

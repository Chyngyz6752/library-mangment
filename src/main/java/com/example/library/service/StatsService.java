package com.example.library.service;

import com.example.library.dto.StatsDto;
import com.example.library.enums.BookCopyStatus;
import com.example.library.enums.LoanStatus;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    @Transactional(readOnly = true)
    public StatsDto getStats() {
        long totalCopies = bookCopyRepository.count();
        long availableCopies = bookCopyRepository.findAll().stream()
                .filter(c -> c.getStatus() == BookCopyStatus.AVAILABLE)
                .count();
        long totalMembers = memberRepository.count();
        long activeMembers = memberRepository.findAll().stream()
                .filter(m -> m.isActive())
                .count();
        return new StatsDto(
                bookRepository.count(),
                totalCopies,
                availableCopies,
                totalMembers,
                activeMembers,
                loanRepository.countByStatus(LoanStatus.ACTIVE),
                loanRepository.countByStatus(LoanStatus.OVERDUE)
        );
    }
}

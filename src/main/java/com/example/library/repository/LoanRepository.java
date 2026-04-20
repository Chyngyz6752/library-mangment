package com.example.library.repository;

import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByMemberAndStatus(Member member, LoanStatus status);

    long countByStatus(LoanStatus status);

    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);

    Page<Loan> findByMember(Member member, Pageable pageable);

    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDate date);
}

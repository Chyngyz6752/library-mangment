package com.example.library.repository;

import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Loan.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByMemberAndStatus(Member member, LoanStatus status);
}
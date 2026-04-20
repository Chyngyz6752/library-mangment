package com.example.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueScheduler {

    private final LoanService loanService;

    @Scheduled(cron = "0 0 3 * * *")
    public void markOverdueLoans() {
        log.debug("Running overdue-loan sweep");
        int count = loanService.markOverdueLoans();
        if (count > 0) {
            log.info("Overdue sweep: {} loans transitioned to OVERDUE", count);
        }
    }
}

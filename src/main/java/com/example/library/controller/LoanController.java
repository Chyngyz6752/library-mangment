package com.example.library.controller;

import com.example.library.dto.BorrowRequestDto;
import com.example.library.dto.LoanDto;
import com.example.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for loan operations.
 * Works exclusively with DTOs.
 */
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrowBook(@Valid @RequestBody BorrowRequestDto borrowRequest) {
        LoanDto loan = loanService.borrowBook(borrowRequest.getMemberId(), borrowRequest.getCopyId());
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanDto> returnBook(@RequestParam Long loanId) {
        LoanDto loan = loanService.returnBook(loanId);
        return ResponseEntity.ok(loan);
    }
}
package com.example.library.controller;

import com.example.library.dto.BorrowRequestDto;
import com.example.library.dto.LoanDto;
import com.example.library.enums.LoanStatus;
import com.example.library.service.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public ResponseEntity<Page<LoanDto>> list(
            @RequestParam(required = false) LoanStatus status,
            @PageableDefault(size = 20, sort = "loanId") Pageable pageable
    ) {
        return ResponseEntity.ok(loanService.getLoans(status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanDto> borrow(@Valid @RequestBody BorrowRequestDto req) {
        LoanDto loan = loanService.borrowBook(req.memberId(), req.copyId());
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanDto> returnBook(@RequestParam @NotNull Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }
}

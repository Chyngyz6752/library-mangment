package com.example.library.controller;

import com.example.library.dto.BookCopyCreateDto;
import com.example.library.dto.BookCopyDto;
import com.example.library.service.BookCopyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book-copies")
@RequiredArgsConstructor
public class BookCopyController {

    private final BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<Page<BookCopyDto>> list(@PageableDefault(size = 20, sort = "copyId") Pageable pageable) {
        return ResponseEntity.ok(bookCopyService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookCopyService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BookCopyDto> create(@Valid @RequestBody BookCopyCreateDto dto) {
        return new ResponseEntity<>(bookCopyService.create(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookCopyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

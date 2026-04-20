package com.example.library.service;

import com.example.library.dto.BookCopyCreateDto;
import com.example.library.dto.BookCopyDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.enums.BookCopyStatus;
import com.example.library.exception.DuplicateResourceException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookCopyMapper;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<BookCopyDto> getAll(Pageable pageable) {
        return bookCopyRepository.findAll(pageable).map(BookCopyMapper::toDto);
    }

    @Transactional(readOnly = true)
    public BookCopyDto getById(Long id) {
        return bookCopyRepository.findById(id)
                .map(BookCopyMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + id));
    }

    @Transactional
    public BookCopyDto create(BookCopyCreateDto dto) {
        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.bookId()));

        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setBarcode(dto.barcode());
        copy.setAccessionNumber(dto.accessionNumber());
        copy.setStatus(dto.status() != null ? dto.status() : BookCopyStatus.AVAILABLE);
        copy.setLocation(dto.location());

        BookCopy saved;
        try {
            saved = bookCopyRepository.save(copy);
            bookCopyRepository.flush();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Book copy with barcode " + dto.barcode() + " already exists");
        }
        log.info("Created book copy id={}, barcode={}", saved.getCopyId(), saved.getBarcode());
        return BookCopyMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        BookCopy copy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + id));
        if (copy.getStatus() == BookCopyStatus.BORROWED) {
            throw new DuplicateResourceException("Cannot delete a borrowed copy. Return it first.");
        }
        bookCopyRepository.delete(copy);
    }
}

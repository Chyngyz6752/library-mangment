package com.example.library.service;

import com.example.library.dto.BookCreateDto;
import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.enums.BookCopyStatus;
import com.example.library.exception.DuplicateResourceException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookCopyRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCopyRepository bookCopyRepository;

    @Transactional(readOnly = true)
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toDtoWithCounts);
    }

    @Transactional(readOnly = true)
    public Page<BookDto> searchBooks(String query, Pageable pageable) {
        if (query == null || query.isBlank()) {
            return getAllBooks(pageable);
        }
        return bookRepository.findByTitleContainingIgnoreCase(query.trim(), pageable)
                .map(this::toDtoWithCounts);
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return toDtoWithCounts(book);
    }

    @Transactional
    public BookDto createBook(BookCreateDto bookDto) {
        if (bookRepository.existsByIsbn(bookDto.isbn())) {
            throw new DuplicateResourceException("Book with ISBN " + bookDto.isbn() + " already exists");
        }
        Category category = loadCategory(bookDto.categoryId());
        Book book = BookMapper.toEntity(bookDto, category);
        Book saved = bookRepository.save(book);
        log.info("Created book id={}, title='{}'", saved.getBookId(), saved.getTitle());
        return toDtoWithCounts(saved);
    }

    @Transactional
    public BookDto updateBook(Long id, BookCreateDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.findByIsbn(bookDto.isbn())
                .filter(other -> !other.getBookId().equals(id))
                .ifPresent(other -> {
                    throw new DuplicateResourceException("Another book already uses ISBN " + bookDto.isbn());
                });
        Category category = loadCategory(bookDto.categoryId());
        BookMapper.applyDto(book, bookDto, category);
        log.info("Updated book id={}", id);
        return toDtoWithCounts(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        long existingCopies = bookCopyRepository.countByBookAndStatus(book, BookCopyStatus.BORROWED);
        if (existingCopies > 0) {
            throw new DuplicateResourceException(
                    "Cannot delete book with borrowed copies. Return all copies first.");
        }
        bookCopyRepository.deleteAll(bookCopyRepository.findByBook(book));
        bookRepository.delete(book);
        log.info("Deleted book id={}", id);
    }

    private Category loadCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    private BookDto toDtoWithCounts(Book book) {
        long available = bookCopyRepository.countByBookAndStatus(book, BookCopyStatus.AVAILABLE);
        return BookMapper.toDto(book, available);
    }
}

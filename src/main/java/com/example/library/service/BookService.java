package com.example.library.service;

import com.example.library.dto.BookCreateDto;
import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления книгами.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDto> searchBooks(String query) {
        if (query == null || query.isBlank()) {
            return getAllBooks();
        }
        return bookRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Transactional
    public BookDto createBook(BookCreateDto bookDto) {
        Category category = categoryRepository.findById(bookDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + bookDto.categoryId()));
        Book book = BookMapper.toEntity(bookDto, category);
        Book savedBook = bookRepository.save(book);
        return BookMapper.toDto(savedBook);
    }

    @Transactional
    public BookDto updateBook(Long id, BookCreateDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        Category category = categoryRepository.findById(bookDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + bookDto.categoryId()));

        book.setTitle(bookDto.title());
        book.setSubtitle(bookDto.subtitle());
        book.setIsbn(bookDto.isbn());
        book.setPublisher(bookDto.publisher());
        book.setPublishYear(bookDto.publishYear());
        book.setLanguage(bookDto.language());
        book.setPages(bookDto.pages());
        book.setTotalCopies(bookDto.totalCopies());
        book.setCategory(category);

        Book updatedBook = bookRepository.save(book);
        return BookMapper.toDto(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
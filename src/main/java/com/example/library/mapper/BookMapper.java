package com.example.library.mapper;

import com.example.library.dto.BookCreateDto;
import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.Category;

import java.time.LocalDateTime;

public class BookMapper {

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublishYear(),
                book.getTotalCopies(),
                book.getPublisher(),
                book.getLanguage(),
                book.getPages()
        );
    }

    public static Book toEntity(BookCreateDto dto, Category category) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setSubtitle(dto.subtitle());
        book.setIsbn(dto.isbn());
        book.setPublisher(dto.publisher());
        book.setPublishYear(dto.publishYear());
        book.setLanguage(dto.language());
        book.setPages(dto.pages());
        book.setTotalCopies(dto.totalCopies());
        book.setCategory(category);
        book.setCreatedAt(LocalDateTime.now());
        return book;
    }
}
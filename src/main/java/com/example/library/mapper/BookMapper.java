package com.example.library.mapper;

import com.example.library.dto.BookCreateDto;
import com.example.library.dto.BookDto;
import com.example.library.entity.Book;
import com.example.library.entity.Category;

public final class BookMapper {

    private BookMapper() {
    }

    public static BookDto toDto(Book book, long availableCopies) {
        Category category = book.getCategory();
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getSubtitle(),
                book.getIsbn(),
                book.getPublishYear(),
                book.getTotalCopies(),
                availableCopies,
                book.getPublisher(),
                book.getLanguage(),
                book.getPages(),
                category != null ? category.getCategoryId() : null,
                category != null ? category.getName() : null,
                book.getCreatedAt()
        );
    }

    public static Book toEntity(BookCreateDto dto, Category category) {
        Book book = new Book();
        applyDto(book, dto, category);
        return book;
    }

    public static void applyDto(Book book, BookCreateDto dto, Category category) {
        book.setTitle(dto.title());
        book.setSubtitle(dto.subtitle());
        book.setIsbn(dto.isbn());
        book.setPublisher(dto.publisher());
        book.setPublishYear(dto.publishYear());
        book.setLanguage(dto.language());
        book.setPages(dto.pages());
        book.setTotalCopies(dto.totalCopies());
        book.setCategory(category);
    }
}

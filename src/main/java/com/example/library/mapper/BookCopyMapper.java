package com.example.library.mapper;

import com.example.library.dto.BookCopyDto;
import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;

public final class BookCopyMapper {

    private BookCopyMapper() {
    }

    public static BookCopyDto toDto(BookCopy copy) {
        Book book = copy.getBook();
        return new BookCopyDto(
                copy.getCopyId(),
                book != null ? book.getBookId() : null,
                book != null ? book.getTitle() : null,
                copy.getBarcode(),
                copy.getAccessionNumber(),
                copy.getStatus(),
                copy.getLocation(),
                copy.getAddedAt()
        );
    }
}

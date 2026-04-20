package com.example.library.mapper;

import com.example.library.dto.AuthorCreateDto;
import com.example.library.dto.AuthorDto;
import com.example.library.entity.Author;

public final class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getAuthorId(),
                author.getFullName(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getBiography()
        );
    }

    public static Author toEntity(AuthorCreateDto dto) {
        Author author = new Author();
        applyDto(author, dto);
        return author;
    }

    public static void applyDto(Author author, AuthorCreateDto dto) {
        author.setFullName(dto.fullName());
        author.setBirthYear(dto.birthYear());
        author.setDeathYear(dto.deathYear());
        author.setBiography(dto.biography());
    }
}

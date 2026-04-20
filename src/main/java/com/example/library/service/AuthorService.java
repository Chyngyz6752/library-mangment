package com.example.library.service;

import com.example.library.dto.AuthorCreateDto;
import com.example.library.dto.AuthorDto;
import com.example.library.entity.Author;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.AuthorMapper;
import com.example.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<AuthorDto> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(AuthorMapper::toDto);
    }

    @Transactional(readOnly = true)
    public AuthorDto getById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Transactional
    public AuthorDto create(AuthorCreateDto dto) {
        Author saved = authorRepository.save(AuthorMapper.toEntity(dto));
        return AuthorMapper.toDto(saved);
    }

    @Transactional
    public AuthorDto update(Long id, AuthorCreateDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        AuthorMapper.applyDto(author, dto);
        return AuthorMapper.toDto(author);
    }

    @Transactional
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
}

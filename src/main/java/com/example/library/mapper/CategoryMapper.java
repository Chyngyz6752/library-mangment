package com.example.library.mapper;

import com.example.library.dto.CategoryCreateDto;
import com.example.library.dto.CategoryDto;
import com.example.library.entity.Category;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toEntity(CategoryCreateDto dto) {
        Category category = new Category();
        applyDto(category, dto);
        return category;
    }

    public static void applyDto(Category category, CategoryCreateDto dto) {
        category.setName(dto.name());
        category.setDescription(dto.description());
    }
}

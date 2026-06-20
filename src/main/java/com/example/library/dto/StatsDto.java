package com.example.library.dto;

/**
 * DTO агрегированной статистики библиотеки для дашборда.
 */

public record StatsDto(
        long totalBooks,
        long totalCopies,
        long availableCopies,
        long totalMembers,
        long activeMembers,
        long activeLoans,
        long overdueLoans
) {
}

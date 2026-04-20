package com.example.library.dto;

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

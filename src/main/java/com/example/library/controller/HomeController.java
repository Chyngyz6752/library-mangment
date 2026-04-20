package com.example.library.controller;

import com.example.library.service.BookService;
import com.example.library.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final StatsService statsService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", statsService.getStats());
        return "index";
    }

    @GetMapping("/books")
    public String showBooks(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model
    ) {
        var pageable = PageRequest.of(Math.max(0, page), Math.min(Math.max(1, size), 100), Sort.by("title"));
        var result = bookService.searchBooks(query, pageable);
        model.addAttribute("books", result.getContent());
        model.addAttribute("query", query == null ? "" : query);
        model.addAttribute("currentPage", result.getNumber());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalElements", result.getTotalElements());
        return "books";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", statsService.getStats());
        return "dashboard";
    }
}

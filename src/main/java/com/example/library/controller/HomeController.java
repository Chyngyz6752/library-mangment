package com.example.library.controller;

import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for displaying HTML pages.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;

    @GetMapping("/")
    public String home() {
        return "index"; // Returns the template name (index.html)
    }

    @GetMapping("/books")
    public String showBooks(@RequestParam(required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(query));
            model.addAttribute("query", query);
        } else {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("query", "");
        }
        return "books"; // Returns the template name (books.html)
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Returns the template name (register.html)
    }
}
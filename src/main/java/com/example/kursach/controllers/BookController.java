package com.example.kursach.controllers;

import com.example.kursach.models.Book;
import com.example.kursach.models.Course;
import com.example.kursach.models.User;
import com.example.kursach.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public String allBooks(@AuthenticationPrincipal User user, Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("user", user);
        return "books";
    }

    @PostMapping("/books")
    public String searchAndSortBooks(@AuthenticationPrincipal User user, @RequestParam String searchBy,
                                     @RequestParam String value, @RequestParam String sortBy, Model model) {
        if (!value.isEmpty()) {
            List<Book> books = bookService.searchBookBy(searchBy, value);
            model.addAttribute("books", books);
        } else {
            List<Book> allBooks = bookService.findAll();
            List<Book> books = bookService.sortBookBy(sortBy, allBooks);
            model.addAttribute("books", books);
        }
        model.addAttribute("user", user);
        return "books";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        return "books-add";
    }

    @PostMapping("/books/add")
    public String addBookPost(@RequestParam String name, @RequestParam String author, @RequestParam String language,
                                @RequestParam String level, @RequestParam String description, Model model) {
        Book book = new Book(name, author, language, level, description);
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String bookInfo(@AuthenticationPrincipal User user, @PathVariable(value = "id") int id, Model model) {
        if (!bookService.existsById(id)) {
            return "redirect:/books";
        }
        List<Book> result = bookService.findById(id);
        model.addAttribute("book", result);
        model.addAttribute("user", user);
        return "book-info";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(@PathVariable(value = "id") int id, Model model) {
        if (!bookService.existsById(id)) {
            return "redirect:/books";
        }
        List<Book> result = bookService.findById(id);
        model.addAttribute("book", result);
        return "book-edit";
    }

    @PostMapping("/books/{id}/edit")
    public String editBookPost(@PathVariable(value = "id") int id, @RequestParam String name, @RequestParam String author,
                               @RequestParam String language, @RequestParam String level, @RequestParam String description,
                               Model model) {
        bookService.editBook(id, name, author, language, level, description);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBookPost(@PathVariable(value = "id") int id, Model model) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}

package com.example.kursach.services;

import com.example.kursach.models.Book;
import com.example.kursach.models.Course;
import com.example.kursach.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> result = new ArrayList<>();
        book.ifPresent(result::add);
        return result;
    }

    public void editBook(int id, String name, String author, String language, String level, String description) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setLanguage(language);
        book.setLevel(level);
        book.setDescription(description);
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
    }

    public boolean existsById(int id) {
        return bookRepository.existsById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> searchBookBy(String searchBy, String value) {
        Iterable<Book> books = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        switch (searchBy) {
            case "По названию": {
                for (Book book : books) {
                    if (book.getName().toLowerCase().contains(value.toLowerCase())) {
                        result.add(book);
                    }
                }
                break;
            }
            case "По языку": {
                for (Book book : books) {
                    if (book.getLanguage().toLowerCase().contains(value.toLowerCase())) {
                        result.add(book);
                    }
                }
                break;
            }
            case "По автору": {
                for (Book book : books) {
                    if (book.getAuthor().toLowerCase().contains(value.toLowerCase())) {
                        result.add(book);
                    }
                }
                break;
            }
        }
        return result;
    }

    public List<Book> sortBookBy(String sortValue, List<Book> books) {
        switch (sortValue) {
            case "По названию": {
                Collections.sort(books, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()) > 1 ? 1 : s1.getName().compareToIgnoreCase(s2.getName()) < 1 ? -1 : 0);
                break;
            }
            case "По автору": {
                Collections.sort(books, (s1, s2) -> s1.getAuthor().compareToIgnoreCase(s2.getAuthor()) > 1 ? 1 : s1.getAuthor().compareToIgnoreCase(s2.getAuthor()) < 1 ? -1 : 0);
                break;
            }
        }
        return books;
    }
}

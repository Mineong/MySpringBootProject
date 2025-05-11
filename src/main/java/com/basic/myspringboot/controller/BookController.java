package com.basic.myspringboot.controller;

import com.basic.myspringboot.data.BookDTO;
import com.basic.myspringboot.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> create(@Valid @RequestBody BookDTO.BookCreateRequest req) {
        return ResponseEntity.ok(bookService.createBook(req));
    }

    @GetMapping
    public List<BookDTO.BookResponse> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/author/{author}")
    public List<BookDTO.BookResponse> getByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.BookUpdateRequest req
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}


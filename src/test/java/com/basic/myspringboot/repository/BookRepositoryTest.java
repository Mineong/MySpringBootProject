package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book(null, "스프링 부트 입문", "홍길동", "9788956746425", 30000, LocalDate.of(2025, 5, 7));
        bookRepository.save(book);
    }

    @Test
    void testFindByIsbn() {
        Optional<Book> book = bookRepository.findByIsbn("9788956746425");
        assertThat(book).isPresent();
    }

    @Test
    void testFindByAuthor() {
        List<Book> books = bookRepository.findByAuthor("홍길동");
        assertThat(books).isNotEmpty();
    }

    @Test
    void testUpdateBook() {
        Book book = bookRepository.findByIsbn("9788956746425").get();
        book.setPrice(25000);
        bookRepository.save(book);
    }

    @Test
    void testDeleteBook() {
        Book book = bookRepository.findByIsbn("9788956746425").get();
        bookRepository.delete(book);
    }
}

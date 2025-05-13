package com.basic.myspringboot.service;

import com.basic.myspringboot.controller.dto.BookDTO;
import com.basic.myspringboot.entity.Book;
import com.basic.myspringboot.entity.BookDetail;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.exception.ErrorCode;
import com.basic.myspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        // Book 엔티티 생성
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        // BookDetail 생성 및 연결
        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book) // 양방향 연결
                    .build();

            book.setBookDetail(detail); // Book → BookDetail 연결
        }

        Book saved = bookRepository.save(book);
        return BookDTO.Response.fromEntity(saved);
    }

    // 2. ISBN으로 책 조회 (BookDetail 포함)
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "isbn", isbn));
        return BookDTO.Response.fromEntity(book);
    }

    // 3. 저자 이름으로 책 검색
    public List<BookDTO.Response> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BusinessException(ErrorCode.AUTHOR_NOT_FOUND, author);
        }

        return books.stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 4. 제목으로 책 검색
    public List<BookDTO.Response> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BusinessException(ErrorCode.TITLE_NOT_FOUND, title);
        }

        return books.stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 5. 전체 책 목록 조회
    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }
}

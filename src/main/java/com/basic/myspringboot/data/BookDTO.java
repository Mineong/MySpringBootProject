package com.basic.myspringboot.data;

import com.basic.myspringboot.entity.Book;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter @Setter
    public static class BookCreateRequest {
        @NotBlank private String title;
        @NotBlank private String author;
        @NotBlank private String isbn;
        @NotNull private Integer price;
        @NotNull private LocalDate publishDate;

        public Book toEntity() {
            return new Book(null, title, author, isbn, price, publishDate);
        }
    }

    @Getter @Setter
    public static class BookUpdateRequest {
        @NotBlank private String title;
        @NotBlank private String author;
        @NotNull private Integer price;
        @NotNull private LocalDate publishDate;
    }

    @Getter @AllArgsConstructor
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return new BookResponse(
                    book.getId(), book.getTitle(), book.getAuthor(),
                    book.getIsbn(), book.getPrice(), book.getPublishDate()
            );
        }
    }
}

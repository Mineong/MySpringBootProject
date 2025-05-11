package com.basic.myspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer price;
    private LocalDate publishDate;
    // Getter/Setter, Constructor 생략
}

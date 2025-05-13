package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Integer pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column
    private String coverImageUrl;

    @Column
    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}

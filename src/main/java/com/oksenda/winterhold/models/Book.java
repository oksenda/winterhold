package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Book")
public class Book{
    @Id
    @Column(name = "Code")
    private String code;
    @Column(name = "Title")
    private String title;
    @Column(name = "Summary")
    private String summary;
    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;
    @Column(name = "TotalPage")
    private Integer totalPage;
    @Column(name = "IsBorrowed")
    private Boolean isBorrowed;
    @ManyToOne
    @JoinColumn(name = "CategoryName")
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "AuthorBook",
            joinColumns = @JoinColumn(name = "AuthorId"),
            inverseJoinColumns = @JoinColumn(name = "CodeBook")
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

}

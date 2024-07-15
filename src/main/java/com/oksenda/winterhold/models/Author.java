package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Author")
public class Author{
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "BirthDate")
    private LocalDate birthDate;
    @Column(name = "Title")
    private String title;
    @Column(name = "Education")
    private String education;
    @Column(name = "Summary")
    private String summary;
    @Column(name = "DeceasedDate")
    private LocalDate deceasedDate;
    @ManyToMany (mappedBy = "authors")
    private List<Book> books;

    public String getFullName(){
        return String.format("%s. %s %s", title, firstName, lastName);
    }
    public  Long getAge(){
        if (deceasedDate != null){
            return ChronoUnit.YEARS.between(birthDate,deceasedDate);

        }else {
            return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        }
    }
}
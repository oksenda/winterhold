package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Loan")
public class Loan{
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "LoanDate")
    private LocalDate loanDate;
    @Column(name = "DueDate")
    private LocalDate dueDate;
    @Column(name = "ReturnDate")
    private LocalDate returnDate;
    @Column(name = "Note")
    private String note;
    @ManyToOne
    @JoinColumn(name = "CustomerNumber")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "BookCode")
    private Book book;
}

package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class LoanItemDto {
    private final BigInteger id;
    private final String bookTitle;
    private final String customerName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate loanDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate returnDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dueDate;
}

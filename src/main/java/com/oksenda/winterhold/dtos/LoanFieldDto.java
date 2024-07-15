package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.models.Book;
import com.oksenda.winterhold.models.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
public class LoanFieldDto {

    private final BigInteger id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate loanDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dueDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate returnDate;
    private final String note;
    private final String customerNumber;
    private final String bookCode;
}

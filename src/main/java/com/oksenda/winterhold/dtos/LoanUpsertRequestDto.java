package com.oksenda.winterhold.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDate;
@Data
@Builder
public class LoanUpsertRequestDto {
    private BigInteger id;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate loanDate;
    private final String note;
    @NotNull
    @NotBlank
    private final String customerNumber;
    @NotNull
    @NotBlank
    private final String bookCode;
}

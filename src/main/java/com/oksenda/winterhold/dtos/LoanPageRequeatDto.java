package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanPageRequeatDto {
    private final Integer pageNumber;
    private final String customerName;
    private final String bookTitle;
}

package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class LoanBookDetailDto {
    private final String title;
    private final String authorName;
    private final Integer totalPage;
    private final String categoryName;
    private final Integer floor;
    private final String bay;
    private final String isle;
}

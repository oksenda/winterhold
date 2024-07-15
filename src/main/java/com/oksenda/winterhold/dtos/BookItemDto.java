package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookItemDto {
    private final String code;
    private final String title;
    private final Boolean isBorrowed;
    private final List<String> authorName;
    private final LocalDate releaseDate;
    private final Integer totalPage;
    private final String summary;
    private final String categoryName;
}

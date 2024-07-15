package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPageRequestDto {
    private final String categoryName;
    private final String title;
    private final String name;
    private final Integer pageNumber;
    private final Boolean isBorrowed;
}

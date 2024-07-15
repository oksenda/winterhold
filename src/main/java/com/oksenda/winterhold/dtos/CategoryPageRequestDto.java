package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryPageRequestDto {
    private final Integer pageNumber;
    private final String name;
}

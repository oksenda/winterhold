package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Builder
@Data
public class CategoryItemDto {
    private final String name;
    private final Integer floor;
    private final String bay;
    private final String isle;
    private final Integer totalBooks;
}

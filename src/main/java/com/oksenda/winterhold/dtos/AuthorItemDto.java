package com.oksenda.winterhold.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Builder
@Data
public class AuthorItemDto {
    private final BigInteger id;
    private final String fullName;
    private final String education;
    private final String isLife;
    private final Long age;
}

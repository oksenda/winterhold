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
public class UpdateBookRequestDto {
    private String code;
    @NotNull
    @NotBlank
    private final String title;
    private final String summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    private final Integer totalPage;
    @NotNull
    private final String categoryName;
    @NotNull
    private final BigInteger authorId;
}

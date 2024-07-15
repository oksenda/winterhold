package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.models.Author;
import com.oksenda.winterhold.models.Category;
import com.oksenda.winterhold.validators.CodeBook;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InsertRequestBookDto {
    @NotNull
    @NotBlank
    @CodeBook
    private String code;
    @NotNull
    @NotBlank
    private String title;
    private String summary;
    private LocalDate releaseDate;
    private Integer totalPage;
    @NotNull
    private String categoryName;
    @NotNull
    private List<BigInteger> authorId;
}

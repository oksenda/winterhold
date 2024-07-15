package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.validators.CategoriName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsertCategoryRequesrDto {
    @NotBlank
    @NotNull
    @CategoriName
    private final String name;
    @NotNull
    private final Integer floor;
    @NotNull
    @NotBlank
    private final String isle;
    @NotNull
    @NotBlank
    private final String bay;


}

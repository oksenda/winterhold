package com.oksenda.winterhold.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoriRequestDto {
    private String name;
    @NotNull
    private final Integer floor;
    @NotNull
    @NotBlank
    private final String isle;
    @NotNull
    @NotBlank
    private final String bay;
}

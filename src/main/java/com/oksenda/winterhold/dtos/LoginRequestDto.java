package com.oksenda.winterhold.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDto {
    @NotBlank
    @NotNull
    private final String username;
    @NotBlank
    @NotNull
    private final String password;
}

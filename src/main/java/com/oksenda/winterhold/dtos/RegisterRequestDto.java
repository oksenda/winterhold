package com.oksenda.winterhold.dtos;

import com.oksenda.winterhold.validators.PasswordConfirm;
import com.oksenda.winterhold.validators.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@PasswordConfirm
public class RegisterRequestDto {
    @NotBlank
    @NotNull
    @Username
    private final String username;
    @NotBlank
    @NotNull
    private final String password;
    @NotBlank
    @NotNull
    private final String confirmPassword;
}

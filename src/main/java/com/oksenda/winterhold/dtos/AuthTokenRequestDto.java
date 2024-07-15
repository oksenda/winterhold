package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenRequestDto {
    private final String username;
    protected final String password;
}

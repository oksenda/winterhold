package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthLoginResponDto {
    private final String token;
}

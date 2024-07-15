package com.oksenda.winterhold.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDto {
    private final String username;
}

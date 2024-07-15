package com.oksenda.winterhold.services;

import com.oksenda.winterhold.dtos.AuthLoginResponDto;
import com.oksenda.winterhold.dtos.AuthTokenRequestDto;
import com.oksenda.winterhold.dtos.RegisterRequestDto;

public interface AuthService {
    void register(RegisterRequestDto dto);
    boolean isUsernameExist(String username);
    AuthLoginResponDto createToken(AuthTokenRequestDto dto);
}

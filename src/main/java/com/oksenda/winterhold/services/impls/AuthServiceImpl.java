package com.oksenda.winterhold.services.impls;

import com.oksenda.winterhold.auth.jwt.JwtService;
import com.oksenda.winterhold.dtos.AuthLoginResponDto;
import com.oksenda.winterhold.dtos.AuthTokenRequestDto;
import com.oksenda.winterhold.dtos.RegisterRequestDto;
import com.oksenda.winterhold.models.Account;
import com.oksenda.winterhold.models.MyAccountDetails;
import com.oksenda.winterhold.repositories.AccountRepository;
import com.oksenda.winterhold.services.AuthService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void register(RegisterRequestDto dto) {
        var hashedPassword= passwordEncoder.encode(dto.getPassword());
        Account account = Account.builder()
                .username(dto.getUsername().toLowerCase())
                .password(hashedPassword)
                .build();
        accountRepository.save(account);
    }

    @Override
    public AuthLoginResponDto createToken(AuthTokenRequestDto dto) {
        var account= accountRepository.findById(dto.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }
        return AuthLoginResponDto.builder()
                .token(jwtService.generteToken(account))
                .build();
    }

    @Override
    public boolean isUsernameExist(String username) {
        return accountRepository.existsById(username.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findById(username.toLowerCase()).orElseThrow(()->
                new IllegalArgumentException("user not found"));
        System.out.println(account);
        return new MyAccountDetails(account);
    }
}

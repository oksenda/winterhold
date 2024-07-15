package com.oksenda.winterhold.controllers.api;


import com.oksenda.winterhold.dtos.AuthLoginResponDto;
import com.oksenda.winterhold.dtos.AuthTokenRequestDto;
import com.oksenda.winterhold.dtos.RegisterRequestDto;
import com.oksenda.winterhold.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<?> createToken(@RequestBody AuthTokenRequestDto dto){
            AuthLoginResponDto responseDto = authService.createToken(dto);
            return ResponseEntity.ok(responseDto);
    }

    @PostMapping("register")
    public ResponseEntity<?> registerSave(@Valid @RequestBody RegisterRequestDto dto, BindingResult bindingResult){
        authService.register(dto);
        return ResponseEntity.ok("Berhasil Register");
    }
}

package com.oksenda.winterhold.validators;

import com.oksenda.winterhold.services.AuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidation implements ConstraintValidator<Username,String> {
    private final AuthService authService;

    public UsernameValidation(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !authService.isUsernameExist(username);
    }
}

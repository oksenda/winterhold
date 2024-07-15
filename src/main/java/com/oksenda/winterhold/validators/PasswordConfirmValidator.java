package com.oksenda.winterhold.validators;


import com.oksenda.winterhold.dtos.RegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, RegisterRequestDto> {

    @Override
    public boolean isValid(RegisterRequestDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}

package com.oksenda.winterhold.validators;

import com.oksenda.winterhold.services.BookService;
import com.oksenda.winterhold.services.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MembershipNumberValidation implements ConstraintValidator<MembershipNumber,String> {
    private final CustomerService customerService;

    public MembershipNumberValidation(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public boolean isValid(String membershipNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !customerService.isExist(membershipNumber);
    }
}

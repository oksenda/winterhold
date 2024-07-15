package com.oksenda.winterhold.validators;

import com.oksenda.winterhold.services.BookService;
import com.oksenda.winterhold.services.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodeBookValidation implements ConstraintValidator<CodeBook,String> {
    private final BookService bookService;

    public CodeBookValidation(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        return !bookService.isExist(code);
    }
}

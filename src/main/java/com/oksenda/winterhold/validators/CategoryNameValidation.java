package com.oksenda.winterhold.validators;

import com.oksenda.winterhold.services.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryNameValidation implements ConstraintValidator<CategoriName,String> {
    private final CategoryService categoryService;

    public CategoryNameValidation(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryService.isCategoryExist(username);
    }
}

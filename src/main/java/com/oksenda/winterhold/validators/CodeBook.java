package com.oksenda.winterhold.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodeBookValidation.class)
public @interface CodeBook {
    String message() default "Code Book Already exits";
    public Class<?>[] groups() default{};
    public Class<?extends Payload>[] payload() default {};
}

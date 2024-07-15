package com.oksenda.winterhold.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidation.class)
public @interface Username {
    String message() default "Username Already exits";
    public Class<?>[] groups() default{};
    public Class<?extends Payload>[] payload() default {};
}

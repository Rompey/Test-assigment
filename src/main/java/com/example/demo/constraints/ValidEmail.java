package com.example.demo.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidEmailValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidEmail {

    String message() default "Invalid email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

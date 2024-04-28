package com.example.demo.constraints;

import com.example.demo.common.UserProperty;
import com.example.demo.exceptions.InvalidAgeException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidAgeValidator implements ConstraintValidator<ValidAge, LocalDateTime> {

    private String message;
    private final UserProperty userProperty;

    @Override
    public void initialize(ValidAge constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LocalDateTime birthDate, ConstraintValidatorContext context) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.minusYears(userProperty.age()).isAfter(birthDate)) {
            return true;
        }
        if (isDateEqual(birthDate, currentDate) && (currentDate.getMonthValue() > birthDate.getMonthValue())) {
            return true;
        }
        if (isDateEqual(birthDate, currentDate) && (currentDate.getMonthValue() == birthDate.getMonthValue()) &&
                currentDate.getDayOfMonth() > birthDate.getDayOfMonth()) {
            return true;
        }

        throw new InvalidAgeException(message);
    }

    private boolean isDateEqual(LocalDateTime birthDate, LocalDateTime currentDate) {
        return currentDate.minusYears(userProperty.age()).isEqual(birthDate);
    }
}

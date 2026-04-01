package com.connorwelsh.auth_portal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;':\",./<>?".indexOf(c) >= 0);
        boolean longEnough = password.length() >= 8;

        if (!longEnough || !hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecial) {
            context.disableDefaultConstraintViolation();

            StringBuilder message = new StringBuilder("Password must contain: ");
            if (!longEnough) message.append("at least 8 characters, ");
            if (!hasUpperCase) message.append("an uppercase letter, ");
            if (!hasLowerCase) message.append("a lowercase letter, ");
            if (!hasDigit) message.append("a digit, ");
            if (!hasSpecial) message.append("a special character, ");
        
            // Remove trailing comma and space
            String msg = message.substring(0, message.length() - 2);
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return false;
        }
        return true;
    }

}

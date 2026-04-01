package com.connorwelsh.auth_portal.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {
    String message() default "Password does not meet the strength requirements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package Tracker.person;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordsValidator.class)
public @interface ValidPasswords {
    String message() default "{passwords.not.valid.error}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
package Tracker.person;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordsValidator implements ConstraintValidator<ValidPasswords, Person> {

    @Override
    public void initialize (ValidPasswords constraintAnnotation) { 
        ConstraintValidator.super.initialize(constraintAnnotation); 
    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {
        if (person.getPassword() == null || person.getPassword().equals("")) {
            if (person.getId() == null) { 
                ctx.disableDefaultConstraintViolation(); 
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("password")
                    .addConstraintViolation();
                return false; 
            } else { 
                return true; 
            }
        }

        boolean passwordsAreValid = person.getPassword().equals(person.getRepeatedPassword());

        if (passwordsAreValid) {
            return true; 
        } else {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addPropertyNode("repeatedPassword") 
                .addConstraintViolation();
            return false;
        } 
    }
}

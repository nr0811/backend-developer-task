package org.nr.backendtask.api.dto;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.nr.backendtask.model.NoteType;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateNoteRequestValidator.class)
@Documented
public @interface CreateNoteValidation {

    String message() default "Invalid content or items";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class CreateNoteRequestValidator implements ConstraintValidator<CreateNoteValidation, NoteRequest> {


    @Override
    public boolean isValid(NoteRequest value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (value.getNoteType() == NoteType.LIST) {
            boolean condition = value.getItems() != null && value.getItems().size() > 0;
            if (!condition) {
                context.buildConstraintViolationWithTemplate("If noteType is list you must pass items").addConstraintViolation();

            }
            return condition;
        } else {
            boolean condition = value.getContent() != null;
            if (!condition) {
                context.buildConstraintViolationWithTemplate("If noteType is text you must pass content").addConstraintViolation();
            }

            return condition;
        }

    }
}

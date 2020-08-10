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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateNoteRequestValidator.class)
@Documented
public @interface CreateNote {

    String message() default "One of content or items must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

 class CreateNoteRequestValidator implements ConstraintValidator<CreateNote,CreateNoteRequest> {



     @Override
     public boolean isValid(CreateNoteRequest value, ConstraintValidatorContext context) {

     }
 }

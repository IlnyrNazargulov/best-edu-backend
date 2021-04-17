package ru.ilnyrdiplom.bestedu.web.utils;

import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValidExerciseFileTypeForCreateValidator.class)
public @interface ValidExerciseFileTypeForCreate {
    ExerciseFileType[] anyOf();

    String message() default "must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
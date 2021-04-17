package ru.ilnyrdiplom.bestedu.web.utils;


import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValidExerciseFileTypeForCreateValidator implements ConstraintValidator<ValidExerciseFileTypeForCreate, ExerciseFileType> {
    private ExerciseFileType[] subset;

    @Override
    public void initialize(ValidExerciseFileTypeForCreate constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(ExerciseFileType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
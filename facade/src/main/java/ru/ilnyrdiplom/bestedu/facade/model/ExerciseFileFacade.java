package ru.ilnyrdiplom.bestedu.facade.model;

import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

public interface ExerciseFileFacade {
    FileFacade getFile();

    ExerciseFileType getExerciseFileType();
}

package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleCreateExerciseFileException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleUpdateExerciseFileException;

import java.io.InputStream;

public interface FileUploadService {
    ExerciseFile createExerciseContentFile(AccountTeacher teacher, Exercise exercise) throws ImpossibleCreateExerciseFileException;

    void updateExerciseContentFile(ExerciseFile exerciseFile, InputStream inputStream)
            throws ImpossibleUpdateExerciseFileException;
}

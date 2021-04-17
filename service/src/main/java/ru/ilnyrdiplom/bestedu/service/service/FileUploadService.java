package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.FileUploadException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleUpdateExerciseFileException;

import java.io.InputStream;

public interface FileUploadService {
    ExerciseFile createEmptyExerciseContentFile(
            InputStream inputStream,
            AccountTeacher teacher,
            Exercise exercise
    ) throws FileUploadException;

    void updateExerciseContentFile(ExerciseFile exerciseFile, InputStream inputStream)
            throws ImpossibleUpdateExerciseFileException;
}

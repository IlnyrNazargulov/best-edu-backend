package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.FileUploadException;

public interface ExerciseFileService {
    ExerciseFile createContentExerciseFile(AccountTeacher teacher, Exercise exercise) throws FileUploadException;
}

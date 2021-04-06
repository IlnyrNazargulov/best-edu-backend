package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

import java.util.UUID;

public interface ExerciseFileService {
    ExerciseFile attachExerciseFile(AccountTeacher teacher, Exercise exercise, ExerciseFileType exerciseFileType, UUID fileUuid)
            throws EntityNotFoundException;

    ExerciseFile getExerciseContentFile(Exercise exercise) throws EntityNotFoundException;
}

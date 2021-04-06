package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.File;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseFileRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseFileService;
import ru.ilnyrdiplom.bestedu.service.service.FileService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExerciseFileServiceImpl implements ExerciseFileService {
    private final FileService fileService;
    private final ExerciseFileRepository exerciseFileRepository;

    @Override
    public ExerciseFile attachExerciseFile(AccountTeacher teacher, Exercise exercise, ExerciseFileType exerciseFileType, UUID fileUuid)
            throws EntityNotFoundException {
        File file = fileService.getFile(fileUuid, teacher);
        ExerciseFile exerciseFile = exerciseFileRepository.findExerciseFile(file);
        exerciseFile.setExercise(exercise);
        exerciseFile.setExerciseFileType(exerciseFileType);
        return exerciseFileRepository.save(exerciseFile);
    }

    @Override
    public ExerciseFile getExerciseContentFile(Exercise exercise) throws EntityNotFoundException {
        ExerciseFile exerciseContentFile = exerciseFileRepository.findExerciseContentFile(exercise);
        if (exerciseContentFile == null) {
            throw new EntityNotFoundException(exercise.getId(), ExerciseFile.class);
        }
        return exerciseContentFile;
    }
}

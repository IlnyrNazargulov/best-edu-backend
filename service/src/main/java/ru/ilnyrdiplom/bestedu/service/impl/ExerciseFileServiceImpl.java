package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseFileRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.ExerciseFileServiceFacade;
import ru.ilnyrdiplom.bestedu.service.config.ExerciseFileProperties;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseFileService;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseService;
import ru.ilnyrdiplom.bestedu.service.service.FileUploadService;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExerciseFileServiceImpl implements ExerciseFileService, ExerciseFileServiceFacade {
    private final ExerciseFileProperties exerciseFileProperties;

    private final ExerciseFileRepository exerciseFileRepository;
    private final ExerciseService exerciseService;
    private final FileUploadService fileUploadService;

    @Override
    @Transactional
    public ExerciseFile deleteExerciseFile(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            UUID fileId
    ) throws ImpossibleAccessDisciplineException, EntityNotFoundException, WrongAccountTypeException {
        Exercise exercise = exerciseService.getAvailableExercise(accountIdentity, disciplineIdentity, exerciseIdentity);
        ExerciseFile exerciseFile = exerciseFileRepository.findExerciseFile(exercise, fileId);
        if (exerciseFile == null) {
            throw new EntityNotFoundException(fileId, ExerciseFile.class);
        }
        exerciseFile.setRemoved(true);
        return exerciseFile;
    }

    @Override
    public List<ExerciseFile> getExerciseFiles(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Exercise exercise = exerciseService.getAvailableExercise(accountIdentity, disciplineIdentity, exerciseIdentity);
        return exerciseFileRepository.findExerciseFiles(exercise);
    }

    @Override
    public ExerciseFile updateExerciseContent(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            String content
    ) throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException, ImpossibleUpdateExerciseFileException, FileUploadException {
        Exercise exercise = exerciseService.getAvailableExercise(accountIdentity, disciplineIdentity, exerciseIdentity);
        if (exercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        ExerciseFile exerciseContentFile = exerciseFileRepository.findExerciseContentFile(exercise);
        if (exerciseContentFile == null) {
            throw new EntityNotFoundException(exercise.getId(), ExerciseFile.class);
        }
        long size = fileUploadService.updateExerciseContentFile(exerciseContentFile, inputStream);
        exerciseFileRepository.updateSize(exerciseContentFile.getFile(), size);
        exerciseContentFile.getFile().setSize(size);
        return exerciseContentFile;
    }

    @Override
    public ExerciseFile createContentExerciseFile(AccountTeacher teacher, Exercise exercise) throws FileUploadException {
        InputStream inputStream = new ByteArrayInputStream(exerciseFileProperties.getEmptyFileText().getBytes());
        return fileUploadService.createEmptyExerciseContentFile(inputStream, teacher, exercise);
    }
}

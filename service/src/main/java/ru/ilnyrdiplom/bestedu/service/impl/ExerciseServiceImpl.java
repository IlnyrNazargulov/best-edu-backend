package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ExerciseRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.model.requests.dto.ExerciseFileDtoFacade;
import ru.ilnyrdiplom.bestedu.facade.services.ExerciseServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseServiceFacade, ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final DisciplineService disciplineService;
    private final AccountService accountService;
    private final ExerciseFileService exerciseFileService;
    private final FileUploadService fileUploadService;

    @Override
    @Transactional
    public Exercise createExercise(AccountIdentity accountIdentity,
                                   DisciplineIdentity disciplineIdentity,
                                   ExerciseRequestFacade exerciseRequest
    )
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException, ExerciseAlreadyExistsException, ImpossibleCreateExerciseFileException {
        Instant now = Instant.now();
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndNameAndIsRemovedFalse(
                discipline,
                exerciseRequest.getName()
        );
        if (existExercise != null) {
            throw new ExerciseAlreadyExistsException(exerciseRequest.getName(), disciplineIdentity);
        }
        Exercise exercise = exerciseRepository.save(Exercise.builder()
                .createdAt(now)
                .name(exerciseRequest.getName())
                .orderNumber(exerciseRequest.getOrderNumber())
                .discipline(discipline)
                .build());
        fileUploadService.createExerciseContentFile(discipline.getTeacher(), exercise);
        return exercise;
    }

    @Override
    public Exercise updateExerciseContent(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            String content
    ) throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException, ImpossibleUpdateExerciseFileException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndIdAndIsRemovedFalse(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        ExerciseFile exerciseContentFile = exerciseFileService.getExerciseContentFile(existExercise);
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        fileUploadService.updateExerciseContentFile(exerciseContentFile, inputStream);
        return existExercise;
    }

    @Override
    public Exercise updateExercise(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            ExerciseRequestFacade exerciseRequest
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        AccountTeacher teacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndIdAndIsRemovedFalse(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        for (ExerciseFileDtoFacade exerciseFileDto : exerciseRequest.getExerciseFiles()) {
            exerciseFileService.attachExerciseFile(teacher, existExercise, exerciseFileDto.getExerciseFileType(), exerciseFileDto.getUuid());
        }
        existExercise.setName(exerciseRequest.getName());
        existExercise.setOrderNumber(exerciseRequest.getOrderNumber());
        return exerciseRepository.save(existExercise);
    }

    @Override
    public Exercise getExercise(AccountIdentity accountIdentity,
                                DisciplineIdentity disciplineIdentity,
                                ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndIdAndIsRemovedFalse(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        return existExercise;
    }

    @Override
    public List<ExerciseWithoutContent> getExercises(AccountIdentity accountIdentity,
                                                     DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        return exerciseRepository.findByDiscipline(discipline);
    }

    @Override
    @Transactional
    public Exercise deleteExercise(AccountIdentity accountIdentity,
                                   DisciplineIdentity disciplineIdentity,
                                   ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndIdAndIsRemovedFalse(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        existExercise.setRemoved(true);
        return existExercise;
    }

    @Override
    public Exercise getExerciseByDiscipline(Discipline discipline, ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException {
        Exercise exercise = exerciseRepository.findByDisciplineAndIdAndIsRemovedFalse(discipline, exerciseIdentity.getId());
        if (exercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        return exercise;
    }
}

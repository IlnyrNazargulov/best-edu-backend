package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ExerciseAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ExerciseRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.services.ExerciseServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.DisciplineService;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseService;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseServiceFacade, ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final DisciplineService disciplineService;
    private final AccountService accountService;

    @Override
    public Exercise createExercise(AccountIdentity accountIdentity,
                                   DisciplineIdentity disciplineIdentity,
                                   ExerciseRequestFacade exerciseRequest
    )
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException, ExerciseAlreadyExistsException {
        Instant now = Instant.now();
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndName(
                discipline,
                exerciseRequest.getName()
        );
        if (existExercise != null) {
            throw new ExerciseAlreadyExistsException(exerciseRequest.getName(), disciplineIdentity);
        }
        Exercise exercise = Exercise.builder()
                .content(exerciseRequest.getContent())
                .createdAt(now)
                .name(exerciseRequest.getName())
                .orderNumber(exerciseRequest.getOrderNumber())
                .discipline(discipline)
                .build();
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            ExerciseRequestFacade exerciseRequest
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndId(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        existExercise.setContent(existExercise.getContent());
        existExercise.setName(existExercise.getName());
        existExercise.setOrderNumber(existExercise.getOrderNumber());
        return exerciseRepository.save(existExercise);
    }

    @Override
    public Exercise getExercise(AccountIdentity accountIdentity,
                                DisciplineIdentity disciplineIdentity,
                                ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise existExercise = exerciseRepository.findByDisciplineAndId(discipline, exerciseIdentity.getId());
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
        Exercise existExercise = exerciseRepository.findByDisciplineAndId(discipline, exerciseIdentity.getId());
        if (existExercise == null) {
            throw new EntityNotFoundException(exerciseIdentity, Exercise.class);
        }
        existExercise.setRemoved(true);
        return existExercise;
    }

    public Exercise checkAccessExercise(AccountIdentity accountIdentity, ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException {
        Account account = accountService.getAccount(accountIdentity);
        if (!(account instanceof AccountTeacher)) {
            throw new WrongAccountTypeException();
        }
        Exercise exercise = exerciseRepository.findByAccountAndId((AccountTeacher) account, exerciseIdentity.getId());
        if (exercise == null) {
            throw new ImpossibleAccessDisciplineException(() -> exercise.getDiscipline().getId(), accountIdentity);
        }
        return exercise;
    }
}

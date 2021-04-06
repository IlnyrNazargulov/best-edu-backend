package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFacade;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseWithoutContentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ExerciseRequestFacade;

import java.util.List;

public interface ExerciseServiceFacade {

    ExerciseFacade updateExerciseContent(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            String content
    ) throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException, ImpossibleUpdateExerciseFileException;

    ExerciseFacade createExercise(AccountIdentity accountIdentity,
                                  DisciplineIdentity disciplineIdentity,
                                  ExerciseRequestFacade exerciseRequest
    )
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException, ExerciseAlreadyExistsException, ImpossibleCreateExerciseFileException;

    ExerciseFacade updateExercise(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            ExerciseRequestFacade exerciseRequest
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;

    ExerciseFacade getExercise(AccountIdentity accountIdentity,
                               DisciplineIdentity disciplineIdentity,
                               ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException;

    List<? extends ExerciseWithoutContentFacade> getExercises(AccountIdentity accountIdentity,
                                                              DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException;

    ExerciseFacade deleteExercise(AccountIdentity accountIdentity,
                                  DisciplineIdentity disciplineIdentity,
                                  ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;
}

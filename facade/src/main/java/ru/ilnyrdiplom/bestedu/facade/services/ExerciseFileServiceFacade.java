package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

import java.util.List;
import java.util.UUID;

public interface ExerciseFileServiceFacade {
    List<? extends ExerciseFileFacade> getExerciseFiles(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;

    ExerciseFileFacade updateExerciseContent(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            String content
    ) throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException, ImpossibleUpdateExerciseFileException, FileUploadException;

    ExerciseFileFacade deleteExerciseFile(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            UUID fileId
    ) throws ImpossibleAccessDisciplineException, EntityNotFoundException, WrongAccountTypeException;
}

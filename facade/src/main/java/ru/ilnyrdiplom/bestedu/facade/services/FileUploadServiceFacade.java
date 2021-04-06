package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface FileUploadServiceFacade {
    ExerciseFileFacade uploadExerciseFile(
            InputStream fileInputStream,
            String fileName,
            AccountIdentity accountIdentity
    ) throws Exception;

    void deleteExerciseFile(
            AccountIdentity accountIdentity,
            UUID fileUuid
    ) throws WrongAccountTypeException, EntityNotFoundException, ImpossibleAccessDisciplineException;
}

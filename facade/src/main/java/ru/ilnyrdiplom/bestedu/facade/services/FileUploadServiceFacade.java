package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface FileUploadServiceFacade {

    List<? extends ExerciseFileFacade> getExerciseFiles(AccountIdentity accountIdentity,
                                                        ExerciseIdentity exerciseIdentity)
            throws WrongAccountTypeException, EntityNotFoundException, ImpossibleAccessDisciplineException;

    ExerciseFileFacade uploadExerciseFile(InputStream fileInputStream, String fileName, AccountIdentity accountIdentity, ExerciseIdentity exerciseIdentity) throws Exception;

    void deleteExerciseFile(
            AccountIdentity accountIdentity,
            ExerciseIdentity exerciseIdentity,
            UUID fileUuid
    ) throws WrongAccountTypeException, EntityNotFoundException, ImpossibleAccessDisciplineException;
}

package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.ImageFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

import java.io.InputStream;

public interface FileUploadServiceFacade {
    ExerciseFileFacade uploadExerciseFile(
            InputStream exerciseFileInputStream,
            String fileName,
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            ExerciseFileType exerciseFileType
    ) throws FileSizeExceededException, FileUploadException, ImpossibleAccessDisciplineException, EntityNotFoundException, WrongAccountTypeException;

    ImageFacade uploadImage(
            InputStream image,
            String fileName,
            AccountIdentity accountIdentity
    ) throws FileUploadException, EntityNotFoundException, FileSizeExceededException;

}

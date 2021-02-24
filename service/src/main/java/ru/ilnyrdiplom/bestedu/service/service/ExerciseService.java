package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

public interface ExerciseService {
    Exercise checkAccessExercise(AccountIdentity accountIdentity, ExerciseIdentity exerciseIdentity)
            throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException;
}

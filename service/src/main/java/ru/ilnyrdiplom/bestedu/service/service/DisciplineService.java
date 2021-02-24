package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

public interface DisciplineService {
    Discipline getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;
}

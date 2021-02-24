package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

import java.util.List;

public interface DisciplineServiceFacade {
    DisciplineFacade createDiscipline(AccountIdentity accountIdentity, String name, boolean isPublic)
            throws EntityNotFoundException, DisciplineAlreadyExistsException;

    DisciplineFacade updateDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity, String name)
            throws EntityNotFoundException;

    DisciplineFacade deleteDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException;

    DisciplineFacade getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;

    List<? extends DisciplineFacade> getDisciplines(
            AccountIdentity accountIdentity,
            AccountIdentity teacherIdentity,
            String teacherFullName,
            String nameDiscipline
    ) throws EntityNotFoundException;

}

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
    DisciplineFacade createDiscipline(AccountIdentity accountIdentity, String name, boolean isPublic, String description)
            throws EntityNotFoundException, DisciplineAlreadyExistsException;

    DisciplineFacade updateDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            String name,
            boolean isPublic,
            String description,
            boolean isVisible
    )
            throws EntityNotFoundException, DisciplineAlreadyExistsException;

    DisciplineFacade deleteDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException;

    DisciplineFacade getAvailableDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException;

    DisciplineFacade getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException;

    List<? extends DisciplineFacade> getDisciplines(
            AccountIdentity accountIdentity,
            AccountIdentity teacherIdentity,
            String teacherFullName,
            String nameDiscipline,
            boolean onlyVisible
    ) throws EntityNotFoundException;

}

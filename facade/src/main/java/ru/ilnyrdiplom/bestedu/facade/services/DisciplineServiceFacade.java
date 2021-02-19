package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

public interface DisciplineServiceFacade {
    DisciplineFacade createDiscipline(AccountIdentity accountIdentity, String name)
            throws EntityNotFoundException, DisciplineAlreadyExistsException;

    DisciplineFacade updateDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity, String name)
            throws EntityNotFoundException;

    DisciplineFacade getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException;
}

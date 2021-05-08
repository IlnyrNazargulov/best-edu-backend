package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccessDisciplineStatusException;
import ru.ilnyrdiplom.bestedu.facade.model.AccessDisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccessDisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

import java.util.List;

public interface AccessDisciplineServiceFacade {
    AccessDisciplineFacade createRequestAccessDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity) throws EntityNotFoundException, WrongAccessDisciplineStatusException;

    AccessDisciplineFacade acceptAccessDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineIdentity accessDisciplineIdentity
    )
            throws EntityNotFoundException, WrongAccessDisciplineStatusException;

    AccessDisciplineFacade rejectAccessDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineIdentity accessDisciplineIdentity
    )
            throws EntityNotFoundException;

    List<? extends AccessDisciplineFacade> getAccessDisciplines(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineStatus status
    ) throws EntityNotFoundException;
}

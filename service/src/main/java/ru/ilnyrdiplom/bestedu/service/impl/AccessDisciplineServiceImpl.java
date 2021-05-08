package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.AccessDiscipline;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.AccessDisciplineRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccessDisciplineStatusException;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccessDisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.AccessDisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.DisciplineService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessDisciplineServiceImpl implements AccessDisciplineServiceFacade {
    private final DisciplineService disciplineService;
    private final AccountService accountService;

    private final AccessDisciplineRepository accessDisciplineRepository;

    @Override
    @Transactional
    public AccessDiscipline createRequestAccessDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity) throws EntityNotFoundException, WrongAccessDisciplineStatusException {
        Account account = accountService.getAccount(accountIdentity);
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        if (discipline.isPublic()) {
            return null;
        }
        AccessDiscipline existAwaitAccessDiscipline = accessDisciplineRepository.findAccessDiscipline(discipline, account);
        if (existAwaitAccessDiscipline != null) {
            if (existAwaitAccessDiscipline.getStatus() == AccessDisciplineStatus.ACCEPTED) {
                throw new WrongAccessDisciplineStatusException();
            }
            existAwaitAccessDiscipline.setStatus(AccessDisciplineStatus.AWAIT);
            return existAwaitAccessDiscipline;
        }
        AccessDiscipline accessDiscipline = new AccessDiscipline(account, discipline);
        return accessDisciplineRepository.save(accessDiscipline);
    }

    @Override
    @Transactional
    public AccessDiscipline acceptAccessDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineIdentity accessDisciplineIdentity
    )
            throws EntityNotFoundException, WrongAccessDisciplineStatusException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline disciplineByTeacher = disciplineService.getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        AccessDiscipline existAwaitAccessDiscipline = accessDisciplineRepository.findAccessDiscipline(disciplineByTeacher, accessDisciplineIdentity.getId());
        if (existAwaitAccessDiscipline == null) {
            throw new EntityNotFoundException(accessDisciplineIdentity, AccessDiscipline.class);
        }
        if (existAwaitAccessDiscipline.getStatus() == AccessDisciplineStatus.REJECTED) {
            throw new WrongAccessDisciplineStatusException();
        }
        existAwaitAccessDiscipline.setStatus(AccessDisciplineStatus.ACCEPTED);
        return existAwaitAccessDiscipline;
    }

    @Override
    @Transactional
    public AccessDiscipline rejectAccessDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineIdentity accessDisciplineIdentity
    )
            throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline disciplineByTeacher = disciplineService.getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        AccessDiscipline existAwaitAccessDiscipline = accessDisciplineRepository.findAccessDiscipline(disciplineByTeacher, accessDisciplineIdentity.getId());
        if (existAwaitAccessDiscipline == null) {
            throw new EntityNotFoundException(accessDisciplineIdentity, AccessDiscipline.class);
        }
        existAwaitAccessDiscipline.setStatus(AccessDisciplineStatus.REJECTED);
        return existAwaitAccessDiscipline;
    }


    @Override
    public List<AccessDiscipline> getAccessDisciplines(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            AccessDisciplineStatus status
    ) throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline disciplineByTeacher = null;
        if (disciplineIdentity.getId() != null) {
            disciplineByTeacher = disciplineService.getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        }
        return accessDisciplineRepository.findAccessDisciplines(accountTeacher, disciplineByTeacher, status);
    }
}

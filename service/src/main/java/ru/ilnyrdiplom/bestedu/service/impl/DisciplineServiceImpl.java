package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.AccessDisciplineRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.DisciplineRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.DisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.DisciplineService;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineServiceFacade, DisciplineService {
    private final AccountService accountService;
    private final DisciplineRepository disciplineRepository;
    private final AccessDisciplineRepository accessDisciplineRepository;

    @Override
    public Discipline createDiscipline(AccountIdentity accountIdentity, String name, boolean isPublic, String description)
            throws EntityNotFoundException, DisciplineAlreadyExistsException {
        Instant now = Instant.now();
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline existDiscipline = disciplineRepository
                .findDisciplineByNameAndTeacherAndIsRemovedFalse(name, accountTeacher);
        if (existDiscipline != null) {
            throw new DisciplineAlreadyExistsException(name, accountIdentity);
        }
        Discipline discipline = new Discipline(accountTeacher, now, name, isPublic, description);
        return disciplineRepository.save(discipline);
    }

    @Override
    public List<Discipline> getDisciplines(
            AccountIdentity accountIdentity,
            AccountIdentity teacherIdentity,
            String teacherFullName,
            String nameDiscipline,
            boolean onlyActive
    ) throws EntityNotFoundException {
        Account account = accountService.getAccount(accountIdentity);
        AccountTeacher teacher = null;
        if (teacherIdentity.getId() != null) {
            teacher = accountService.getAccountTeacher(teacherIdentity);
        }
        return disciplineRepository.findDisciplines(account, teacher, teacherFullName, nameDiscipline, onlyActive);
    }

    @Override
    public Discipline getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        Discipline availableDiscipline = disciplineRepository
                .findAvailableDiscipline(disciplineIdentity.getId(), accountIdentity.getId());
        if (availableDiscipline == null) {
            throw new EntityNotFoundException(disciplineIdentity, Discipline.class);
        }
        return availableDiscipline;
    }

    @Override
    @Transactional
    public Discipline updateDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            String newName,
            boolean isPublic,
            String description
    )
            throws EntityNotFoundException, DisciplineAlreadyExistsException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        Discipline existDiscipline = disciplineRepository
                .findDisciplineByNameAndTeacherAndIsRemovedFalse(newName, accountTeacher);
        if (existDiscipline != null && !existDiscipline.getId().equals(disciplineIdentity.getId())) {
            throw new DisciplineAlreadyExistsException(newName, accountIdentity);
        }
        discipline.setName(newName);
        discipline.setPublic(isPublic);
        discipline.setDescription(description);
        return discipline;
    }

    @Override
    @Transactional
    public Discipline deleteDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        discipline.setRemoved(true);
        return discipline;
    }

    private Discipline getDisciplineByTeacher(AccountTeacher accountTeacher, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        Discipline discipline = disciplineRepository
                .findDisciplineByIdAndTeacherAndIsRemovedFalse(disciplineIdentity.getId(), accountTeacher);
        if (discipline == null) {
            throw new EntityNotFoundException(disciplineIdentity, Discipline.class);
        }
        return discipline;
    }
}
